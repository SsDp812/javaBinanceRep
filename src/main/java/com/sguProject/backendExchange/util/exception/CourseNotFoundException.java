package com.sguProject.backendExchange.util.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        this(message, null);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
