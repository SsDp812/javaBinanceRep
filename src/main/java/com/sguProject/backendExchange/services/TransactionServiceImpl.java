package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Transaction;
import com.sguProject.backendExchange.repositories.TransactionRepository;
import com.sguProject.backendExchange.services.interfaces.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions(Account account) {
        return transactionRepository.findAllBySellerIdOrBuyerId(account.getId(), account.getId());
    }
}
