package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    Currency create(Currency currency);

    List<Currency> getAllCurrencies();

    Optional<Currency> findByTicker(String ticker);

    Currency getByTicker(String ticker);

    Optional<Currency> findByName(String name);
}
