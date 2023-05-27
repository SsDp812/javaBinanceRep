package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByAccountId(int accountId);
    Page<Transaction> findAllByAccountId(int accountId, Pageable pageable);
}
