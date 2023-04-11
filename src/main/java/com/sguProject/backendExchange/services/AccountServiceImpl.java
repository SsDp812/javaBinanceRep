package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.repositories.AccountRepository;
import com.sguProject.backendExchange.security.AccountDetails;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account register(Account account) {
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
