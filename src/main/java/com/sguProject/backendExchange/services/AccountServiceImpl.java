package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.repositories.AccountRepository;
import com.sguProject.backendExchange.security.AccountDetails;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private static final String INITIAL_BALANCE_TICKER = "USDT";
    private static final double INITIAL_BALANCE_AMOUNT = 30_000;

    private final AccountRepository accountRepository;
    private final CurrencyService currencyService;

    public AccountServiceImpl(AccountRepository accountRepository, CurrencyService currencyService) {
        this.accountRepository = accountRepository;
        this.currencyService = currencyService;
    }

    @Transactional
    @Override
    public Account register(Account account) {
        Balance accountBalance = new Balance();
        accountBalance.setCurrency(currencyService.getByTicker(INITIAL_BALANCE_TICKER));
        accountBalance.setAmount(INITIAL_BALANCE_AMOUNT);
        accountBalance.setOwner(account);

        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account getById(int id) {
        return accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account getAccountCurrentSession() {
        final Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        final AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();

        return accountDetails.getAccount();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new AccountDetails(account.get());
    }
}
