package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.CurrencyPairService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import com.sguProject.backendExchange.services.interfaces.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trading")
public class TradingRestController {

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final CurrencyPairService currencyPairService;
    private final ExchangeService exchangeService;

    @Autowired
    public TradingRestController(AccountService accountService, CurrencyService currencyService, CurrencyPairService currencyPairService, ExchangeService exchangeService) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.currencyPairService = currencyPairService;
        this.exchangeService = exchangeService;
    }

    @PutMapping("/sellQuoted")
    public ResponseEntity<HttpStatus> sellQuoted(@RequestParam(name = "base") String baseTicker,
                                              @RequestParam(name = "quoted") String quotedTicker,
                                              @RequestParam(name = "quantity") double quantity) {
        Account currentUser = accountService.getAccountCurrentSession();

        Currency base = currencyService.getByTicker(baseTicker);
        Currency quoted = currencyService.getByTicker(quotedTicker);

        CurrencyPair currencyPair = currencyPairService.findByBaseAndQuoted(base, quoted)
                .orElseThrow(IllegalArgumentException::new);

        exchangeService.sellQuoted(currentUser, currencyPair, quantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/sellBase")
    public ResponseEntity<HttpStatus> sellBase(@RequestParam(name = "base") String baseTicker,
                                               @RequestParam(name = "quoted") String quotedTicker,
                                               @RequestParam(name = "quantity") double quantity) {
        Account currentUser = accountService.getAccountCurrentSession();

        Currency base = currencyService.getByTicker(baseTicker);
        Currency quoted = currencyService.getByTicker(quotedTicker);

        CurrencyPair currencyPair = currencyPairService.findByBaseAndQuoted(base, quoted)
                .orElseThrow(IllegalArgumentException::new);

        exchangeService.sellBase(currentUser, currencyPair, quantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
