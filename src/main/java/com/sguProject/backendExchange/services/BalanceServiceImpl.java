package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.repositories.AccountRepository;
import com.sguProject.backendExchange.repositories.BalanceRepository;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import com.sguProject.backendExchange.util.exception.BalanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class BalanceServiceImpl implements BalanceService {

    private final static double INITIAL_BALANCE_AMOUNT = 0;

    private final BalanceRepository balanceRepository;
    private final AccountRepository accountRepository;

    private final AccountService accountService;
    private final CurrencyService currencyService;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository, AccountRepository accountRepository,
                              AccountService accountService, CurrencyService currencyService) {
        this.balanceRepository = balanceRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.currencyService = currencyService;
    }

    @Transactional
    @Override
    public Balance create(Account owner, Currency currency) {
        owner = accountRepository.save(owner);
        Balance balance = new Balance(currency, owner, INITIAL_BALANCE_AMOUNT);

        return balanceRepository.save(balance);
    }

    @Override
    public Balance getUserBalanceBy(String currencyTicker) {
        Account account = accountService.getAccountCurrentSession();

        return balanceRepository.findByOwnerAndCurrency(account, currencyService.getByTicker(currencyTicker))
                .orElseThrow(() -> new BalanceNotFoundException(account.getId(), currencyTicker));
    }

    @Override
    public Set<Balance> getUserAllBalances() {
        Account account = accountService.getAccountCurrentSession();

        return balanceRepository.findAllByOwner(account);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void withdraw(Account account, Currency currency, double amount) {
        Balance balance = balanceRepository.findByOwnerAndCurrency(account, currency)
                .orElseThrow(() -> new BalanceNotFoundException(account.getId(), currency.getTicker()));

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
