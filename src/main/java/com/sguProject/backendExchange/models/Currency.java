package com.sguProject.backendExchange.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Currency")
public class Currency { // None, BTC, ETH, BNB, USDT, XRP, LTC, BCH;

    @Id
    @NotEmpty(message = "Ticker should not be empty")
    @Column(name = "ticker", nullable = false, unique = true)
    private String ticker;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "Currency_Exchangeable",
            joinColumns = @JoinColumn(name = "currency_ticker"),
            inverseJoinColumns = @JoinColumn(name = "exchangeable_ticker")
    )
    private Set<Currency> exchangeables;

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

    public Set<Currency> getExchangeables() {
        return exchangeables;
    }

    public void setExchangeables(Set<Currency> exchangeables) {
        this.exchangeables = exchangeables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (!Objects.equals(ticker, currency.ticker)) return false;
        if (!Objects.equals(name, currency.name)) return false;
        return Objects.equals(exchangeables, currency.exchangeables);
    }

    @Override
    public int hashCode() {
        int result = ticker != null ? ticker.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (exchangeables != null ? exchangeables.hashCode() : 0);
        return result;
    }
}
