package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class BalanceRestController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceRestController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/buy")
    public void buy(@RequestParam("coin1") String buyableTicker,
                    @RequestParam("coin2") String salableTicker,
                    @RequestParam("number") double number) {
        balanceService.buyCurrency(buyableTicker, salableTicker, number);
    }

    @GetMapping("/sell")
    public void sell(@RequestParam("coin1") String salableTicker,
                     @RequestParam("coin2") String buyableTicker,
                     @RequestParam("number") double number) {
        balanceService.sellCurrency(salableTicker, buyableTicker, number);
    }

    @GetMapping("/getBalanceAll")
    public List<Balance> getAllBalances() {
        return balanceService.getAll();
    }

    @GetMapping("/getBalance/{coin}")
    public double getBalance(@PathVariable String coin){
        throw new NotYetImplementedException(); // TODO: Добавить возвращение баланса текущего аккаунта
    }
}
