package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.repositories.CurrencyRepository;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import com.sguProject.backendExchange.util.exception.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Override
    public Currency create(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Optional<Currency> findByTicker(String ticker) {
        return currencyRepository.findByTicker(ticker);
    }

    @Override
    public Currency getByTicker(String ticker) {
        return currencyRepository.findByTicker(ticker)
                .orElseThrow(() -> new CurrencyNotFoundException(ticker));
    }

    @Override
    public Optional<Currency> findByName(String name) {
        return currencyRepository.findByName(name);
    }
}
