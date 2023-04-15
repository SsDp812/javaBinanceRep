package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.services.interfaces.ExchangeService;
import com.sguProject.backendExchange.util.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trading")
public class TradingRestController {

    private final ExchangeService exchangeService;

    @Autowired
    public TradingRestController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PutMapping("/exchange")
    public ResponseEntity<HttpStatus> exchange(@RequestParam(name = "base") String baseTicker,
                                              @RequestParam(name = "quoted") String quotedTicker,
                                              @RequestParam(name = "quantity") double quantity,
                                              @RequestParam(name = "operation") String operationName) {
        Operation operation = Operation.valueOf(operationName);

        exchangeService.exchange(baseTicker, quotedTicker, quantity, operation);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
