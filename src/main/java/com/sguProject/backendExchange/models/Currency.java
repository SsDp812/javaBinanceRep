package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "Currency")
public class Currency { // None, BTC, ETH, BNB, USDT, XRP, LTC, BCH;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Ticker should not be empty")
    @Column(name = "ticker")
    private String ticker;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (id != currency.id) return false;
        if (!Objects.equals(ticker, currency.ticker)) return false;
        return Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ticker != null ? ticker.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
