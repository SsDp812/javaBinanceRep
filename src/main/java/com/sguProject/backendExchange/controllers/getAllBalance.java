package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.services.BalanceDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("getBalanceAll")
public class getAllBalance {
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping()
    public List<Coin> getBalance(){
        BalanceDAO dao = new BalanceDAO();
        return dao.getAllCoins();
    }
}
