package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Transaction;
import com.sguProject.backendExchange.services.TransactionDAO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class historyController {
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("history")
    public List<Transaction> getHistory(){
        TransactionDAO dao = new TransactionDAO();
        return dao.getAllTransactions();
    }

}
