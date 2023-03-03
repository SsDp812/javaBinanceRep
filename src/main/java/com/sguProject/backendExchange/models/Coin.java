package com.sguProject.backendExchange.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "Coin")
public class Coin {
    public enum CoinType {
        None, BTC, ETH, BNB, USDT, XRP, LTC, BCH;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "type")
    private CoinType type;

    @Column(name = "balance")
    @Enumerated(EnumType.STRING)
    @Min(value = 0, message = "Message should not be less than 0")
    private double balance;

    public Coin() { }

    public Coin(CoinType type, double balance) {
        setType(type);
        setBalance(balance);
    }

    public CoinType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() { return type.name(); }

    public void setType(CoinType type) {
        if (type == CoinType.None) {
            throw new IllegalArgumentException("Coin type cannot be CoinType.None");
        }

        this.type = type;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("balance cannot be less than 0");
        }

        this.balance = balance;
    }

    public void add(double numberOfCoins) {
        if (0 > numberOfCoins) {
            throw new IllegalArgumentException("numberOfCoins cannot be less or equal to zero");
        }

        balance += numberOfCoins;
    }

    public void decrease(double numberOfCoins) {
        if (numberOfCoins > balance) {
            throw new IllegalArgumentException("numberOfCoins cannot be more than balance");
        }

        balance -= numberOfCoins;
    }

    @Override
    public String toString() {
        return type.name() + " " + balance;
    }
}
