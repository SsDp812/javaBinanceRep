package com.sguProject.backendExchange.util.enums;

public enum Operation {
    SELL("Sell"), BUY("Buy");

    private final String presentName;

    Operation(String presentName) {
        this.presentName = presentName;
    }

    @Override
    public String toString() {
        return presentName;
    }
}
