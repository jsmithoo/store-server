package com.example.domain.exception;

public class NotFoundException extends RuntimeException {

    private static final String PATTERN_MESSAGE = "La entidad %s con id %s no existe";

    public NotFoundException(String entityType, String id) {
        super(String.format(PATTERN_MESSAGE, entityType, id));
    }
}
