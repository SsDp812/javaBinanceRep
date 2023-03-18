package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;

import java.util.List;
import java.util.Optional;

public interface BalanceService {
    void create(Account owner, Currency currency);

    List<Balance> getAll();

    List<Balance> getAllByOwner(Account owner);

    Optional<Balance> findBy(Account owner, Currency currency);

    void sellCurrency(Account owner, Currency salable, Currency buyable, double saleAmount);

    void buyCurrency(Account owner, Currency buyable, Currency salable, double buyAmount)
}
