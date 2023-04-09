package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.CurrencyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trading")
public class TradingController {

    private static final String TRADING = "trading";

    private final CurrencyPairService currencyPairService;

    @Autowired
    public TradingController(CurrencyPairService currencyPairService) {
        this.currencyPairService = currencyPairService;
    }

    @GetMapping()
    public String index(Model model) {
        List<CurrencyPair> currencyPairs = currencyPairService.getAll();

        model.addAttribute("currencyPairs", currencyPairs);

        return TRADING;
    }
}
