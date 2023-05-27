package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.models.Transaction;
import com.sguProject.backendExchange.util.enums.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    Transaction create(Account account, CurrencyPair currencyPair, Operation operation, double course, double quantity);

    List<Transaction> getAllTransactions(Account account);

    Page<Transaction> getAllTransactions(Account account, Pageable pageable);
}
