package com.example.domain.exception;

public class MandatoryParameterException extends RuntimeException {

    private static final String PATTERN_MESSAGE = "El campo %s es requerido";

    public MandatoryParameterException(String fieldName) {
        super(String.format(PATTERN_MESSAGE, fieldName));
    }
}
