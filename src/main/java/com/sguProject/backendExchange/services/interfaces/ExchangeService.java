package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.CurrencyPair;

public interface ExchangeService {

    void sellBase(Account account, CurrencyPair currencyPair, double quantity);

    void sellQuoted(Account account, CurrencyPair currencyPair, double quantity);
}
