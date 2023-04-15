package com.sguProject.backendExchange.util.exception;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException(long id, String currencyTicker) {
        this(id, currencyTicker, null);
    }

    public BalanceNotFoundException(long id, String currencyTicker, Throwable cause) {
        super("Balance user with id:"+id+" and currency:'"+currencyTicker+"' not found", cause);
    }
}
