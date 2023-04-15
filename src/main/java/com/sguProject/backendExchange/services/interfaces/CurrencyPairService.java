package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;

import java.util.List;
import java.util.Optional;

public interface CurrencyPairService {
    CurrencyPair getByBaseAndQuoted(String baseTicker, String quotedTicker);

    Optional<CurrencyPair> findByBaseAndQuoted(Currency base, Currency quoted);

    List<CurrencyPair> getAll();

    CurrencyPair getById(int id);

}
