package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Entity
@Table(name = "Balance",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "owner_id", "currency_ticker" }) })
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "currency_ticker", referencedColumnName = "ticker")
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
        setAmount(amount);
        setOwner(owner);
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
        if (currency == null)
            throw new NullPointerException("currency should not be null");

        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("amount should not be less than 0");

        this.amount = amount;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        if (owner == null)
            throw new NullPointerException("owner should not be null");

        owner.addBalance(this);
        this.owner = owner;
    }

    public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Withdraw amount should be greater than 0");
        if (this.amount < amount)
            throw new IllegalArgumentException("Withdraw amount is less than current amount");

        this.amount -= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount should be greater than 0");

        this.amount += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (id != balance.id) return false;
        if (Double.compare(balance.amount, amount) != 0) return false;
        if (!Objects.equals(currency, balance.currency)) return false;
        return Objects.equals(owner, balance.owner);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
