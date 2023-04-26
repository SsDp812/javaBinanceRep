package com.sguProject.backendExchange.util.exception.dto;

public class ErrorResponse {

    private String message;
    private long timestamp;

    public ErrorResponse(String message) {
        this(message, System.currentTimeMillis());
    }

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
