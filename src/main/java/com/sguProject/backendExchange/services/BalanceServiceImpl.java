package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.Transaction;
import com.sguProject.backendExchange.repositories.BalanceRepository;
import com.sguProject.backendExchange.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BalanceServiceImpl implements BalanceService {

    private final static double INITIAL_BALANCE_AMOUNT = 0;

    private final BalanceRepository balanceRepository;

    private final TransactionService transactionService;

    private final AccountService accountService;

    private final CourseService courseService;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository, CurrencyService currencyService, TransactionService transactionService, AccountService accountService, CourseService courseService) {
        this.balanceRepository = balanceRepository;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.courseService = courseService;
    }

    @Transactional
    @Override
    public void create(Account owner, Currency currency) {
        Balance balance = new Balance(currency, owner, INITIAL_BALANCE_AMOUNT);

        owner.addBalance(balance);
    }

    @Override
    public List<Balance> getAll() {
        return balanceRepository.findAll();
    }

    @Override
    public List<Balance> getAllByOwner(Account owner) {
        return balanceRepository.findAllByOwner(owner);
    }

    @Override
    public Optional<Balance> findBy(Account owner, Currency currency) {
        return balanceRepository.findByOwnerAndCurrency(owner, currency);
    }

    @Transactional
    @Override
    public void sellCurrency(Account owner, Currency salable, Currency buyable, double saleAmount) {
        Balance salableBalance = balanceRepository.findByOwnerAndCurrency(owner, salable)
                .orElseThrow(IllegalArgumentException::new);
        Balance buyableBalance = balanceRepository.findByOwnerAndCurrency(owner, buyable)
                .orElseThrow(IllegalArgumentException::new);

        double buyAmount = getBuyAmount(buyable, salable, saleAmount);

        salableBalance.withdraw(saleAmount);

        buyableBalance.deposit(buyAmount);

        createTransaction(owner, accountService.getBankAccount(),
                buyable, buyAmount, salable, saleAmount);
    }

    @Transactional
    @Override
    public void buyCurrency(Account owner, Currency buyable, Currency salable, double buyAmount) {
        Balance salableBalance = balanceRepository.findByOwnerAndCurrency(owner, salable)
                .orElseThrow(IllegalArgumentException::new);
        Balance buyableBalance = balanceRepository.findByOwnerAndCurrency(owner, buyable)
                .orElseThrow(IllegalArgumentException::new);

        double requiredSaleAmount = getSaleAmount(buyable, salable, buyAmount);

        if (requiredSaleAmount > salableBalance.getAmount()) {
            throw new IllegalArgumentException("Salable balance amount less than needs for success trade");
        }

        salableBalance.withdraw(requiredSaleAmount);

        buyableBalance.deposit(buyAmount);

        createTransaction(accountService.getBankAccount(), owner,
                buyable, buyAmount, salable, requiredSaleAmount);
    }

    private void createTransaction(Account seller, Account buyer,
                                   Currency buyable, double buyAmount, Currency salable, double saleAmount) {
        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setBuyer(buyer);
        transaction.setPurchasedCurrency(buyable);
        transaction.setPurchasedAmount(buyAmount);
        transaction.setSoldCurrency(salable);
        transaction.setSoldAmount(saleAmount);

        transactionService.create(transaction);
    }

    private double getBuyAmount(Currency buyable, Currency salable, double salableAmount) {
        return salableAmount / getCourse(buyable, salable);
    }

    private double getSaleAmount(Currency buyable, Currency salable, double buyableAmount) {
        return buyableAmount * getCourse(buyable, salable);
    }

    private double getCourse(Currency dividend, Currency divisor) {
        return courseService.getCourse(dividend, divisor);
    }
}
