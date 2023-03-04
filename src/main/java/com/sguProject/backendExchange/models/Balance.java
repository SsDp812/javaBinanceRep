package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Balance",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "owner_id", "currency_id" }) })
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @PositiveOrZero(message = "Amount should be positive or zero")
    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Account owner;

    public Balance() {}

    public Balance(Currency currency, Account owner, double amount) {
        setCurrency(currency);
        setOwner(owner);
        setAmount(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
