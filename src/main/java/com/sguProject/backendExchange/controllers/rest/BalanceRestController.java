package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.dto.AccountBalanceDTO;
import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/balance")
public class BalanceRestController {

    private final AccountService accountService;
    private final BalanceService balanceService;
    private final CurrencyService currencyService;

    @Autowired
    public BalanceRestController(AccountService accountService,
                                 BalanceService balanceService, CurrencyService currencyService) {
        this.accountService = accountService;
        this.balanceService = balanceService;
        this.currencyService = currencyService;
    }

    @GetMapping()
    public List<AccountBalanceDTO> getBalances() {
        Account currentUser = accountService.getAccountCurrentSession();
        Set<Balance> balances = balanceService.getAllByOwner(currentUser);
        return balances.stream().map(balance -> new AccountBalanceDTO(balance)).collect(Collectors.toList());
    }

    @GetMapping("/{currencyTicker}")
    public double getBalance(@PathVariable String currencyTicker) {
        Currency currency = currencyService.getByTicker(currencyTicker);
        Account account = accountService.getAccountCurrentSession();
        Optional<Balance> balance = balanceService.findBy(account, currency);

        if (balance.isEmpty())
            return 0;

        return balance.get().getAmount();
    }
}
