package com.sguProject.backendExchange.util.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpNotFoundException extends RuntimeException {
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public HttpNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
