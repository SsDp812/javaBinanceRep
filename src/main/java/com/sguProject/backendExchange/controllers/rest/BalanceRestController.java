package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.dto.AccountBalanceDTO;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/balance")
public class BalanceRestController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceRestController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping()
    public List<AccountBalanceDTO> getBalances() {
        Set<Balance> balances = balanceService.getUserAllBalances();

        return balances.stream().map(AccountBalanceDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{currencyTicker}")
    public double getBalance(@PathVariable String currencyTicker) {
        Balance balance = balanceService.getUserBalanceBy(currencyTicker);

        return balance.getAmount();
    }
}
