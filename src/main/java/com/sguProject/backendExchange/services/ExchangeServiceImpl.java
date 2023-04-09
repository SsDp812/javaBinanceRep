package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CourseService;
import com.sguProject.backendExchange.services.interfaces.ExchangeService;
import com.sguProject.backendExchange.services.interfaces.TransactionService;
import com.sguProject.backendExchange.util.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final BalanceService balanceService;

    private final CourseService courseService;

    private final TransactionService transactionService;

    @Autowired
    public ExchangeServiceImpl(BalanceService balanceService, CourseService courseService, TransactionService transactionService) {
        this.balanceService = balanceService;
        this.courseService = courseService;
        this.transactionService = transactionService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void sellQuoted(Account account, CurrencyPair currencyPair, double quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("quantity can not be negative or zero");

        double course = courseService.getCourse(currencyPair);
        double purchased = quantity / course;

        balanceService.withdraw(account, currencyPair.getQuoted(), quantity);
        balanceService.deposit(account, currencyPair.getBase(), purchased);

        transactionService.create(account, currencyPair, Operation.BUY, course, purchased);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void sellBase(Account account, CurrencyPair currencyPair, double quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("quantity can not be negative or zero");

        double course = courseService.getCourse(currencyPair);
        double cost = quantity * course;

        balanceService.withdraw(account, currencyPair.getBase(), quantity);
        balanceService.deposit(account, currencyPair.getQuoted(), cost);

        transactionService.create(account, currencyPair, Operation.SELL, course, quantity);
    }
}
