package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BalanceService {
    Balance create(Account owner, Currency currency);

    List<Balance> getAll();

    Balance getUserBalanceBy(String currencyTicker);

    Set<Balance> getAllByOwner(Account owner);

    Optional<Balance> findBy(Account owner, Currency currency);

    void withdraw(Account account, Currency currency, double amount);

    void deposit(Account account, Currency currency, double amount);
}
