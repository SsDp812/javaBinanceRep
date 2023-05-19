package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.*;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.services.interfaces.*;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final AccountService accountService;
    private final BalanceService balanceService;
    private final CurrencyPairService currencyPairService;
    private final CourseService courseService;
    private final TransactionService transactionService;
    private final LimitOrderService limitOrderService;

    @Autowired
    public ExchangeServiceImpl(AccountService accountService, BalanceService balanceService,
                               CurrencyPairService currencyPairService, CourseService courseService,
                               TransactionService transactionService, LimitOrderService limitOrderService) {
        this.accountService = accountService;
        this.balanceService = balanceService;
        this.currencyPairService = currencyPairService;
        this.courseService = courseService;
        this.transactionService = transactionService;
        this.limitOrderService = limitOrderService;
    }

    @Transactional
    @Override
    public void exchange(String baseTicker, String quotedTicker, double quantity, Operation operation) {
        Account account = accountService.getAccountCurrentSession();
        CurrencyPair currencyPair = currencyPairService.getByBaseAndQuoted(baseTicker, quotedTicker);

        double course = courseService.getCourse(currencyPair);

        exchange(account, currencyPair, quantity, operation, course);
    }

    @Transactional
    @Override
    public void exchangeLimit(String baseTicker, String quotedTicker, double quantity, Operation operation, double targetCourse) {
        Account account = accountService.getAccountCurrentSession();
        CurrencyPair currencyPair = currencyPairService.getByBaseAndQuoted(baseTicker, quotedTicker);

        Currency salableCurrency = operation == Operation.BUY ? currencyPair.getQuoted() : currencyPair.getBase();
        Set<Balance> balances = account.getBalances();
        Optional<Balance> salableBalance = balances.stream()
                .filter(balance -> balance.getCurrency().equals(salableCurrency))
                .findFirst();

        if (salableBalance.isEmpty() || salableBalance.get().getAmount() < quantity)
            throw new IllegalArgumentException("withdraw amount is greater than current amount");

        limitOrderService.create(account, currencyPair, quantity, operation, targetCourse);
    }

    private void exchange(Account account, CurrencyPair currencyPair, double quantity, Operation operation, double course) {
        if (quantity <= 0)
            throw new IllegalArgumentException("quantity can not be negative or zero");

        double amount = operation == Operation.BUY ? quantity / course : quantity * course;

        Currency salable = operation == Operation.BUY ? currencyPair.getQuoted() : currencyPair.getBase();
        Currency buyable = operation == Operation.BUY ? currencyPair.getBase() : currencyPair.getQuoted();

        balanceService.withdraw(account, salable, quantity);
        balanceService.deposit(account, buyable, amount);

        transactionService.create(account, currencyPair, operation, course, operation == Operation.BUY ? amount : quantity);
    }

    @Transactional
    @Scheduled(fixedRateString = "${limitOrdersCheck.fixedRate}")
    public void executeLimitOrders() {
        List<LimitOrder> limitOrders = limitOrderService.getAll(OrderStatus.CREATED);
        if (limitOrders.size() == 0)
            return;

        Map<CurrencyPair, Double> currencyPairCourses = new HashMap<>();
        limitOrders.stream()
                .filter(limitOrder -> {
                    double currentCourse = currencyPairCourses.computeIfAbsent(limitOrder.getCurrencyPair(),
                            pair -> courseService.getCourse(limitOrder.getCurrencyPair()));
                    return isLimitOrderExecutable(limitOrder, currentCourse);
                })
                .forEach(limitOrder -> {
                    exchange(limitOrder.getOwner(),
                            limitOrder.getCurrencyPair(),
                            limitOrder.getQuantity(),
                            limitOrder.getOperation(),
                            currencyPairCourses.get(limitOrder.getCurrencyPair()));

                    limitOrder.complete();
                });
    }

    private static boolean isLimitOrderExecutable(LimitOrder limitOrder, double currentCourse) {
        if (limitOrder.getOperation() == Operation.SELL)
            return currentCourse >= limitOrder.getTargetCourse();
        else
            return currentCourse <= limitOrder.getTargetCourse();
    }
}
