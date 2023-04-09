package com.sguProject.backendExchange.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CurrencyPair",
        uniqueConstraints =
        @UniqueConstraint(name = "base_quoted_unique", columnNames = { "base_ticker", "quoted_ticker" }))
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "base_ticker", referencedColumnName = "ticker", nullable = false)
    private Currency base;

    @ManyToOne
    @JoinColumn(name = "quoted_ticker", referencedColumnName = "ticker", nullable = false)
    private Currency quoted;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = base;
    }

    public Currency getQuoted() {
        return quoted;
    }

    public void setQuoted(Currency quoted) {
        this.quoted = quoted;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return base.getTicker()+'/'+quoted.getTicker();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyPair that = (CurrencyPair) o;

        if (id != that.id) return false;
        if (isAvailable != that.isAvailable) return false;
        if (!Objects.equals(base, that.base)) return false;
        return Objects.equals(quoted, that.quoted);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (quoted != null ? quoted.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        return result;
    }
}
