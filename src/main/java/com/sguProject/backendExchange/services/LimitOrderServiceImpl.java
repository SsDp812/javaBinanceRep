package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.*;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.repositories.LimitOrderRepository;
import com.sguProject.backendExchange.services.interfaces.*;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class LimitOrderServiceImpl implements LimitOrderService {

    private final LimitOrderRepository limitOrderRepository;
    private final AccountService accountService;
    private final CurrencyPairService currencyPairService;
    private final CourseService courseService;
    private final ExchangeService exchangeService;

    @Autowired
    public LimitOrderServiceImpl(LimitOrderRepository limitOrderRepository, AccountService accountService, CurrencyPairService currencyPairService, CourseService courseService, ExchangeService exchangeService) {
        this.limitOrderRepository = limitOrderRepository;
        this.accountService = accountService;
        this.currencyPairService = currencyPairService;
        this.courseService = courseService;
        this.exchangeService = exchangeService;
    }


    @Override
    public List<LimitOrder> getAll(OrderStatus status) {
        return limitOrderRepository.findAllByStatus(status);
    }

    @Override
    public List<LimitOrder> getAll(Account owner) {
        return limitOrderRepository.findAllByOwner(owner);
    }

    @Override
    public void delete(int id) {
        limitOrderRepository.deleteById(id);
    }

    @Transactional
    @Override
    public LimitOrder create(Account owner, String baseTicker, String quotedTicker, double quantity, Operation operation, double targetCourse) {
        final CurrencyPair currencyPair = currencyPairService.getByBaseAndQuoted(baseTicker, quotedTicker);

        final Currency salableCurrency = operation == Operation.BUY ? currencyPair.getQuoted() : currencyPair.getBase();
        Set<Balance> balances = owner.getBalances();
        Optional<Balance> salableBalance = balances.stream()
                .filter(balance -> balance.getCurrency().equals(salableCurrency))
                .findFirst();

        if (salableBalance.isEmpty() || salableBalance.get().getAmount() < quantity)
            throw new IllegalArgumentException("withdraw amount is greater than current amount");

        final LimitOrder limitOrder = new LimitOrder();
        limitOrder.setOwner(owner);
        limitOrder.setCurrencyPair(currencyPair);
        limitOrder.setQuantity(quantity);
        limitOrder.setOperation(operation);
        limitOrder.setTargetCourse(targetCourse);
        limitOrder.setStatus(OrderStatus.CREATED);

        return limitOrderRepository.save(limitOrder);
    }

    @Transactional
    @Scheduled(fixedRateString = "${limitOrdersCheck.fixedRate}")
    public void executeLimitOrders() {
        List<LimitOrder> limitOrders = getAll(OrderStatus.CREATED);
        if (limitOrders.size() == 0)
            return;

        Map<CurrencyPair, Double> currencyPairCourses = new HashMap<>();
        limitOrders.stream()
                .filter(limitOrder -> {
                    double currentCourse = currencyPairCourses.computeIfAbsent(limitOrder.getCurrencyPair(),
                            pair -> courseService.getCourse(limitOrder.getCurrencyPair()));
                    return limitOrder.isExecutable(currentCourse);
                })
                .forEach(limitOrder -> {
                    exchangeService.exchange(limitOrder.getOwner(),
                            limitOrder.getCurrencyPair(),
                            limitOrder.getQuantity(),
                            limitOrder.getOperation(),
                            currencyPairCourses.get(limitOrder.getCurrencyPair()));

                    limitOrder.complete();
                });
    }
}
