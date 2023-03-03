package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;

import java.util.List;
import java.util.Optional;

public interface BalanceService {
    void create(Account owner, Currency currency);

    List<BalanceService> getAllByOwner(Account owner);

    Optional<Balance> findBy(Account owner, Currency currency);

    void saleCurrency(Currency salable, Currency buyable, double sale);

    void saleCurrency(String salableTicker, String buyableTicker, double sale);

    void buyCurrency(Currency buyable, Currency salable, double buy);

    void buyCurrency(String buyableTicker, String salableTicker, double buy);
}
