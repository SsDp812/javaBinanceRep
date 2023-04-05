package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AccountService extends UserDetailsService {
    void create(Account account);

    Optional<Account> findById(int id);

    Account getBankAccount();
}
