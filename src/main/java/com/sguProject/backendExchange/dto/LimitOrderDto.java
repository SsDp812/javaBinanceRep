package com.sguProject.backendExchange.dto;

import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.models.LimitOrder;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.enums.OrderStatus;

import java.time.LocalDateTime;

public class LimitOrderDto {
    private int id;
    private int owner_id;
    private CurrencyPair currencyPair;
    private double quantity;
    private Operation operation;
    private double targetCourse;
    private OrderStatus status;
    private LocalDateTime eventDate;

    public LimitOrderDto(LimitOrder limitOrder) {
        id = limitOrder.getId();
        owner_id = limitOrder.getOwner().getId();
        currencyPair = limitOrder.getCurrencyPair();
        quantity = limitOrder.getQuantity();
        operation = limitOrder.getOperation();
        targetCourse = limitOrder.getTargetCourse();
        status = limitOrder.getStatus();
        eventDate = limitOrder.getEventDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
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
}
