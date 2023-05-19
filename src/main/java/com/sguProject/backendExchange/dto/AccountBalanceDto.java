package com.sguProject.backendExchange.dto;

import com.sguProject.backendExchange.models.Balance;
import com.sguProject.backendExchange.models.Currency;

public class AccountBalanceDto {

    private final int id;

    private final Currency currency;

    private final double amount;

    public AccountBalanceDto(int id, Currency currency, double amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
    }

    public AccountBalanceDto(Balance balance) {
        this(balance.getId(), balance.getCurrency(), balance.getAmount());
    }

    public int getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }
}
