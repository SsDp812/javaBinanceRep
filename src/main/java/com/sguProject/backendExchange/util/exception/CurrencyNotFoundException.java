package com.sguProject.backendExchange.util.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String ticker) {
        super("Currency " + ticker + " not found");
    }
}
