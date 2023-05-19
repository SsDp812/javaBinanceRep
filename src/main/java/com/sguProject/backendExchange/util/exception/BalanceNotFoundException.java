package com.sguProject.backendExchange.util.exception;

public class BalanceNotFoundException extends HttpNotFoundException {
    public BalanceNotFoundException(long id, String currencyTicker) {
        this(id, currencyTicker, null);
    }

    public BalanceNotFoundException(long id, String currencyTicker, Throwable cause) {
        super("Balance account with id:"+id+" and currency:'"+currencyTicker+"' not found", cause);
    }
}
