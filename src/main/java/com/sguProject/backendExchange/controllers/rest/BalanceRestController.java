package com.sguProject.backendExchange.controllers.rest;

import com.sguProject.backendExchange.dto.AccountBalanceDto;
import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.util.exception.HttpNotFoundException;
import com.sguProject.backendExchange.util.exception.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/balance")
public class BalanceRestController {

    private final BalanceService balanceService;

    @Autowired
    public BalanceRestController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping()
    public List<AccountBalanceDto> getBalances() {
        Set<Balance> balances = balanceService.getUserAllBalances();

        return balances.stream().map(AccountBalanceDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{currencyTicker}")
    public AccountBalanceDto getBalance(@PathVariable String currencyTicker) {
        Balance balance = balanceService.getUserBalanceBy(currencyTicker);

        return new AccountBalanceDto(balance);
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
