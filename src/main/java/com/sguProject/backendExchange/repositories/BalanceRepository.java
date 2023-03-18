package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findAllByOwner(Account owner);

    Optional<Balance> findByOwnerAndCurrency(Account owner, Currency currency);
}
