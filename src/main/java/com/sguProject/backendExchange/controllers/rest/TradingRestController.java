package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.dto.LimitOrderDto;
import com.sguProject.backendExchange.models.LimitOrder;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.ExchangeService;
import com.sguProject.backendExchange.services.interfaces.LimitOrderService;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.exception.HttpNotFoundException;
import com.sguProject.backendExchange.util.exception.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trading")
public class TradingRestController {

    private final ExchangeService exchangeService;
    private final LimitOrderService limitOrderService;
    private final AccountService accountService;

    @Autowired
    public TradingRestController(ExchangeService exchangeService, LimitOrderService limitOrderService, AccountService accountService) {
        this.exchangeService = exchangeService;
        this.limitOrderService = limitOrderService;
        this.accountService = accountService;
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


    @PutMapping("/limitOrder")
    public ResponseEntity<HttpStatus> createLimitOrder(@RequestParam(name = "base") String baseTicker,
                                                    @RequestParam(name = "quoted") String quotedTicker,
                                                    @RequestParam(name = "quantity") double quantity,
                                                    @RequestParam(name = "course") double targetCourse,
                                                    @RequestParam(name = "operation") String operationName) {
        Operation operation = Operation.valueOf(operationName);

        limitOrderService.create(accountService.getAccountCurrentSession(),
                baseTicker, quotedTicker, quantity, operation, targetCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/limitOrder")
    public List<LimitOrderDto> getLimitOrders() {
        return limitOrderService.getAll(accountService.getAccountCurrentSession()).stream()
                .map(LimitOrderDto::new).collect(Collectors.toList());
    }


    @DeleteMapping("/limitOrder/{id}")
    public ResponseEntity<HttpStatus> cancelLimitOrder(@PathVariable int id) {
        limitOrderService.delete(id);

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
