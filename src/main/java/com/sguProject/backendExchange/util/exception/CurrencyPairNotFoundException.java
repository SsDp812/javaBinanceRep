package com.sguProject.backendExchange.util.exception;

public class CurrencyPairNotFoundException extends HttpNotFoundException {
    public CurrencyPairNotFoundException(String baseTicker, String quotedTicker) {
        this(baseTicker, quotedTicker, null);
    }

    public CurrencyPairNotFoundException(String baseTicker, String quotedTicker, Throwable cause) {
        super("Currency pair " + baseTicker+'/'+quotedTicker + " not found", cause);
    }
}
