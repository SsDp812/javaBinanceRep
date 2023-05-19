package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    Optional<Currency> findByTicker(String ticker);

    Optional<Currency> findByName(String name);
}
