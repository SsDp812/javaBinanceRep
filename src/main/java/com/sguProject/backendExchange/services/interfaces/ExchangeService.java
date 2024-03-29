package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.models.LimitOrder;
import com.sguProject.backendExchange.util.enums.Operation;

import java.util.Set;

public interface ExchangeService {
    void exchange(String baseTicker, String quotedTicker, double quantity, Operation operation);

    void exchange(Account account, CurrencyPair currencyPair, double quantity, Operation operation, double course);
}
