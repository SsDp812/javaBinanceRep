package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;

import java.util.Optional;

public interface AccountService {
    void create(Account account);

    Optional<Account> findById(int id);

    Account getBankAccount();
}
