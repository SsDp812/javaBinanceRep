package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.services.interfaces.ExchangeService;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.exception.HttpNotFoundException;
import com.sguProject.backendExchange.util.exception.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/exchangeLimit")
    public ResponseEntity<HttpStatus> exchangeLimit(@RequestParam(name = "base") String baseTicker,
                                                    @RequestParam(name = "quoted") String quotedTicker,
                                                    @RequestParam(name = "quantity") double quantity,
                                                    @RequestParam(name = "course") double targetCourse,
                                                    @RequestParam(name = "operation") String operationName) {
        Operation operation = Operation.valueOf(operationName);

        exchangeService.exchangeLimit(baseTicker, quotedTicker, quantity, operation, targetCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(HttpNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(status.getReasonPhrase()), status);
    }
}
