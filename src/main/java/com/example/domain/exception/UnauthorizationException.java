package com.example.domain.exception;

public class UnauthorizationException extends RuntimeException {
    private static final String PATTERN_MESSAGE = "Invalid username or password";

    public UnauthorizationException() {
        super(PATTERN_MESSAGE);
    }
}
