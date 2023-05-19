package com.sguProject.backendExchange.util.exception;

public class AccountNotFoundException extends HttpNotFoundException {
    public AccountNotFoundException(long id) {
        this(id, null);
    }

    public AccountNotFoundException(long id, Throwable cause) {
        super("Account with id: "+id+" not found", cause);
    }
}
