package com.sguProject.backendExchange.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "created_at", "seller_id", "buyer_id" })})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Account seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Account buyer;

    @ManyToOne
    @JoinColumn(name = "purchased_id", referencedColumnName = "id")
    private Currency purchasedCurrency;

    @Positive
    @Column(name = "purchased_amount")
    private double purchasedAmount;

    @ManyToOne
    @JoinColumn(name = "sold_id", referencedColumnName = "id")
    private Currency soldCurrency;

    @Positive
    @Column(name = "sold_amount")
    private double soldAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }

    public Currency getPurchasedCurrency() {
        return purchasedCurrency;
    }

    public void setPurchasedCurrency(Currency purchasedCurrency) {
        this.purchasedCurrency = purchasedCurrency;
    }

    public double getPurchasedAmount() {
        return purchasedAmount;
    }

    public void setPurchasedAmount(double purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }

    public Currency getSoldCurrency() {
        return soldCurrency;
    }

    public void setSoldCurrency(Currency soldCurrency) {
        this.soldCurrency = soldCurrency;
    }

    public double getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(double soldAmount) {
        this.soldAmount = soldAmount;
    }
}
