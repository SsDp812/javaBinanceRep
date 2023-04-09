package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {
    Optional<CurrencyPair> findByBaseAndQuoted(Currency base, Currency quoted);
}
