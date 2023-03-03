package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/buy")
    public void buy(@RequestParam("coin1") String buyableTicker,
                    @RequestParam("coin2") String salableTicker,
                    @RequestParam("number") double number) {
        balanceService.buyCurrency(buyableTicker, salableTicker, number);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/sell")
    public void sell(@RequestParam("coin1") String salableTicker,
                     @RequestParam("coin2") String buyableTicker,
                     @RequestParam("number") double number) {
        balanceService.sellCurrency(salableTicker, buyableTicker, number);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/getBalanceAll")
    public List<Balance> getAllBalances() {
        return balanceService.getAll();
    }
}
