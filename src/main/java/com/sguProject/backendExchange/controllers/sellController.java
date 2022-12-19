package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.services.BalanceDAO;
import com.sguProject.backendExchange.services.BalanceLogic;
import com.sguProject.backendExchange.services.TransactionDAO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sellController {
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/sell")
    public void buy(@RequestParam("coin1") String coin1, @RequestParam("coin2") String coin2, @RequestParam("number") double number){
        BalanceLogic logic = new BalanceLogic(new BalanceDAO(),new TransactionDAO());
        logic.changeCoins(Coin.CoinType.valueOf(coin1),Coin.CoinType.valueOf(coin2),number,"SELL");
    }
}
