package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.repositories.AccountRepository;
import com.sguProject.backendExchange.security.AccountDetails;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private static final String INITIAL_BALANCE_TICKER = "USDT";
    private static final double INITIAL_BALANCE_AMOUNT = 30_000;

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final CurrencyService currencyService;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, CurrencyService currencyService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.currencyService = currencyService;
    }

    @Transactional
    @Override
    public Account register(Account account) {
        createInitialBalance().setOwner(account);

        account.setPassword(passwordEncoder.encode(account.getPassword()));
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

    private Balance createInitialBalance() {
        final Balance balance = new Balance();
        balance.setCurrency(currencyService.getByTicker(INITIAL_BALANCE_TICKER));
        balance.setAmount(INITIAL_BALANCE_AMOUNT);

        return balance;
    }
}
