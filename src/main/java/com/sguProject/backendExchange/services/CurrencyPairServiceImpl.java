package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.repositories.CurrencyPairRepository;
import com.sguProject.backendExchange.services.interfaces.CurrencyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CurrencyPairServiceImpl implements CurrencyPairService {

    private final CurrencyPairRepository currencyPairRepository;

    @Autowired
    public CurrencyPairServiceImpl(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }

    @Override
    public Optional<CurrencyPair> findById(int id) {
        return currencyPairRepository.findById(id);
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
