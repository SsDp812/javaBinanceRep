package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Transaction;
import com.sguProject.backendExchange.security.AccountDetails;
import com.sguProject.backendExchange.services.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private TransactionService transactionService;

    @Autowired
    public HistoryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public String index(Model model, @PageableDefault(size = 15, sort = { "createdAt" }, direction = Sort.Direction.DESC) Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        Page<Transaction> page = transactionService.getAllTransactions(accountDetails.getAccount(), pageable);
        model.addAttribute("page", page);

        return "history";
    }
}
