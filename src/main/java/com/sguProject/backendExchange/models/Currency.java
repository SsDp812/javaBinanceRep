package com.sguProject.backendExchange.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "Currency")
public class Currency {

    @Id
    @NotEmpty(message = "Ticker should not be empty")
    @Column(name = "ticker", nullable = false, unique = true)
    private String ticker;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name", nullable = false)
    private String name;

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

        if (!Objects.equals(ticker, currency.ticker)) return false;
        return Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        int result = ticker != null ? ticker.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
