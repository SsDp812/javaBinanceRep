package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
}
