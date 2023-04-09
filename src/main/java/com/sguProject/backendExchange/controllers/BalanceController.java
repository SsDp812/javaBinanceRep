package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    private static final String BALANCE = "balance";
    private static final String ATTRIBUTE_NAME_BALANCES = "balances";

    private final BalanceService balanceService;
    private final AccountService accountService;

    @Autowired
    public BalanceController(BalanceService balanceService, AccountService accountService) {
        this.balanceService = balanceService;
        this.accountService = accountService;
    }

    @GetMapping()
    public String index(Model model) {
        Set<Balance> balances = balanceService.getAllByOwner(accountService.getAccountCurrentSession());

        model.addAttribute(ATTRIBUTE_NAME_BALANCES, balances);
        return BALANCE;
    }
}
