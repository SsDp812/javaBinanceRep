package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class buyController {

    private final BalanceService balanceService;
    private final CurrencyService currencyService;

    @Autowired
    public buyController(BalanceService balanceService, CurrencyService currencyService) {
        this.balanceService = balanceService;
        this.currencyService = currencyService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/buy")
    public void buy(@RequestParam("coin1") String buyableTicker,
                    @RequestParam("coin2") String salableTicker,
                    @RequestParam("number") double number) {
        balanceService.buyCurrency(buyableTicker, salableTicker, number);
    }
}
