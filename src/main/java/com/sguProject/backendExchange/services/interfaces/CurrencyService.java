package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    void add(Currency currency);

    List<Currency> getAllCurrencies();

    Optional<Currency> findByTicker(String ticker);

    Optional<Currency> findByName(String name);
}
