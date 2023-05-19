package com.sguProject.backendExchange.models;

import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "limit_order")
public class LimitOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id", nullable = false)
    private CurrencyPair currencyPair;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operation", nullable = false)
    private Operation operation;

    @Column(name = "target_course", nullable = false)
    private double targetCourse;


    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private OrderStatus status;


    @Column(name = "event_date")
    private LocalDateTime eventDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public double getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(double targetCourse) {
        this.targetCourse = targetCourse;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LimitOrder that = (LimitOrder) o;

        if (id != that.id) return false;
        if (Double.compare(that.quantity, quantity) != 0) return false;
        if (Double.compare(that.targetCourse, targetCourse) != 0) return false;
        if (!Objects.equals(owner, that.owner)) return false;
        if (!Objects.equals(currencyPair, that.currencyPair)) return false;
        if (operation != that.operation) return false;
        if (status != that.status) return false;
        return Objects.equals(eventDate, that.eventDate);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (currencyPair != null ? currencyPair.hashCode() : 0);
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        temp = Double.doubleToLongBits(targetCourse);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (eventDate != null ? eventDate.hashCode() : 0);
        return result;
    }

    public void complete() {
        setStatus(OrderStatus.COMPLETED);
        setEventDate(LocalDateTime.now());
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELED);
        setEventDate(LocalDateTime.now());
    }
}
