package com.sguProject.backendExchange.util.exception;

public class CurrencyNotFoundException extends HttpNotFoundException {
    public CurrencyNotFoundException(String ticker) {
        this(ticker, null);
    }

    public CurrencyNotFoundException(String ticker, Throwable cause) {
        super("Currency " + ticker + " not found", cause);
    }
}
