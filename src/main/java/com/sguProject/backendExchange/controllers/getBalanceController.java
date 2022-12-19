package com.sguProject.backendExchange.controllers;


import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.services.BalanceDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("getBalance")
public class getBalanceController{

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("{coin}")
    public double getBalance(@PathVariable String coin){
        BalanceDAO dao = new BalanceDAO();
        System.out.println(coin);
        double response = dao.getCoin(Coin.CoinType.valueOf(coin)).getBalance();
        return response;
    }

}
