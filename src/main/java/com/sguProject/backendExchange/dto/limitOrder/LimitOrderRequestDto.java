package com.sguProject.backendExchange.dto.limitOrder;

import com.sguProject.backendExchange.util.enums.Operation;

public class LimitOrderRequestDto {
    private String baseTicker;
    private String quotedTicker;
    private double quantity;
    private double targetCourse;
    private Operation operation;

    public LimitOrderRequestDto() {}

    public LimitOrderRequestDto(String baseTicker, String quotedTicker, double quantity, double targetCourse, Operation operation) {
        this.baseTicker = baseTicker;
        this.quotedTicker = quotedTicker;
        this.quantity = quantity;
        this.targetCourse = targetCourse;
        this.operation = operation;
    }

    public String getBaseTicker() {
        return baseTicker;
    }

    public void setBaseTicker(String baseTicker) {
        this.baseTicker = baseTicker;
    }

    public String getQuotedTicker() {
        return quotedTicker;
    }

    public void setQuotedTicker(String quotedTicker) {
        this.quotedTicker = quotedTicker;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(double targetCourse) {
        this.targetCourse = targetCourse;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
