package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.repositories.CurrencyPairRepository;
import com.sguProject.backendExchange.services.interfaces.CurrencyPairService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import com.sguProject.backendExchange.util.exception.CurrencyNotFoundException;
import com.sguProject.backendExchange.util.exception.CurrencyPairNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CurrencyPairServiceImpl implements CurrencyPairService {

    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyPairServiceImpl(CurrencyPairRepository currencyPairRepository, CurrencyService currencyService) {
        this.currencyPairRepository = currencyPairRepository;
        this.currencyService = currencyService;
    }

    @Override
    public CurrencyPair getByBaseAndQuoted(String baseTicker, String quotedTicker) {
        try {
            return findByBaseAndQuoted(currencyService.getByTicker(baseTicker), currencyService.getByTicker(quotedTicker))
                    .orElseThrow(() -> new CurrencyPairNotFoundException(baseTicker, quotedTicker));
        }
        catch (CurrencyNotFoundException e) {
            throw new CurrencyPairNotFoundException(baseTicker, quotedTicker, e);
        }
    }

    @Override
    public Optional<CurrencyPair> findByBaseAndQuoted(Currency base, Currency quoted) {
        return currencyPairRepository.findByBaseAndQuoted(base, quoted);
    }

    @Override
    public List<CurrencyPair> getAll() {
        return currencyPairRepository.findAll();
    }

    @Override
    public CurrencyPair getById(int id) {
        return currencyPairRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
