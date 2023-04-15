package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;

import java.util.Set;

public interface BalanceService {
    Balance create(Account owner, Currency currency);

    Balance getUserBalanceBy(String currencyTicker);

    Set<Balance> getUserAllBalances();

    void withdraw(Account account, Currency currency, double amount);

    void deposit(Account account, Currency currency, double amount);

}
