package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.repositories.BalanceRepository;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class BalanceServiceImpl implements BalanceService {

    private final static double INITIAL_BALANCE_AMOUNT = 0;

    private final BalanceRepository balanceRepository;

    private final AccountService accountService;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository, AccountService accountService) {
        this.balanceRepository = balanceRepository;
        this.accountService = accountService;
    }

    @Transactional
    @Override
    public Balance create(Account owner, Currency currency) {
        Balance balance = new Balance(currency, owner, INITIAL_BALANCE_AMOUNT);
        return balanceRepository.save(balance);
    }

    @Override
    public List<Balance> getAll() {
        return balanceRepository.findAll();
    }

    @Override
    public Set<Balance> getAllByOwner(Account owner) {
        return balanceRepository.findAllByOwner(owner);
    }

    @Override
    public Optional<Balance> findBy(Account owner, Currency currency) {
        return balanceRepository.findByOwnerAndCurrency(owner, currency);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void withdraw(Account account, Currency currency, double amount) {
        Balance balance = balanceRepository.findByOwnerAndCurrency(account, currency)
                .orElseThrow(NoSuchElementException::new);

        if (amount > balance.getAmount()) {
            throw new IllegalArgumentException("Balance amount less than needs for success trade");
        }

        balance.withdraw(amount);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void deposit(Account account, Currency currency, double amount) {
        Balance balance = balanceRepository.findByOwnerAndCurrency(account, currency)
                .orElseGet(() -> create(account, currency));

        balance.deposit(amount);
    }
}
