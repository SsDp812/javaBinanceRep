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
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CoinType type;

    @Column(name = "balance")
    @Min(value = 0, message = "Message should not be less than 0")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coin coin = (Coin) o;

        if (id != coin.id) return false;
        return type == coin.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
