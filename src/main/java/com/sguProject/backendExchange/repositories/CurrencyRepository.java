package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
