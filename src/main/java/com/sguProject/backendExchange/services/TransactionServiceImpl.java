package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.models.Transaction;
import com.sguProject.backendExchange.repositories.TransactionRepository;
import com.sguProject.backendExchange.services.interfaces.TransactionService;
import com.sguProject.backendExchange.util.enums.Operation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(Account account, CurrencyPair currencyPair, Operation operation, double course, double quantity) {
        Transaction transaction = new Transaction();
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setCurrencyPair(currencyPair);
        transaction.setOperation(operation);
        transaction.setCourse(course);
        transaction.setQuantity(quantity);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions(Account account) {
        return transactionRepository.findAllByAccountId(account.getId());
    }
}
