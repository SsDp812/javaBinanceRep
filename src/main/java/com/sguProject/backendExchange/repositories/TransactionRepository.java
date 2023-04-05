package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllBySellerIdOrBuyerId(int seller, int buyer);
}
