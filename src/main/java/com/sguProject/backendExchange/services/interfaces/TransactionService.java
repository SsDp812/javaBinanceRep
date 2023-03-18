package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Transaction;

import java.util.List;

public interface TransactionService {
    void create(Transaction transaction);

    List<Transaction> getAllTransactions(Account account);
}
