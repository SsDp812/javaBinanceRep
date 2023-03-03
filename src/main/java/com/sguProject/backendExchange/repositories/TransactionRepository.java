package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
