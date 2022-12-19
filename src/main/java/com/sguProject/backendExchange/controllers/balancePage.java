package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.services.BalanceDAO;
import com.sguProject.backendExchange.services.BalanceLogic;
import com.sguProject.backendExchange.services.TransactionDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class balancePage {
    @RequestMapping(value="/balance")
    public ModelAndView welcomepage() {
        return new ModelAndView("balance");
    }
}
