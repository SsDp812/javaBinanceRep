package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.*;
import com.sguProject.backendExchange.util.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final AccountService accountService;
    private final BalanceService balanceService;
    private final CurrencyPairService currencyPairService;
    private final CourseService courseService;
    private final TransactionService transactionService;

    @Autowired
    public ExchangeServiceImpl(AccountService accountService, BalanceService balanceService,
                               CurrencyPairService currencyPairService, CourseService courseService,
                               TransactionService transactionService) {
        this.accountService = accountService;
        this.balanceService = balanceService;
        this.currencyPairService = currencyPairService;
        this.courseService = courseService;
        this.transactionService = transactionService;
    }

    @Override
    public void exchange(String baseTicker, String quotedTicker, double quantity, Operation operation) {
        Account account = accountService.getAccountCurrentSession();
        CurrencyPair currencyPair = currencyPairService.getByBaseAndQuoted(baseTicker, quotedTicker);

        exchange(account, currencyPair, quantity, operation);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void exchange(Account account, CurrencyPair currencyPair, double quantity, Operation operation) {
        if (quantity <= 0)
            throw new IllegalArgumentException("quantity can not be negative or zero");

        double course = courseService.getCourse(currencyPair);
        double amount = operation == Operation.BUY ? quantity / course : quantity * course;

        Currency salable = operation == Operation.BUY ? currencyPair.getQuoted() : currencyPair.getBase();
        Currency buyable = operation == Operation.BUY ? currencyPair.getBase() : currencyPair.getQuoted();

        balanceService.withdraw(account, salable, quantity);
        balanceService.deposit(account, buyable, amount);

        transactionService.create(account, currencyPair, operation, course, amount);
    }
}
