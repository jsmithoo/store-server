package com.example.adapters.inbound.controller.handler;

import com.example.domain.exception.MandatoryParameterException;
import com.example.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerNotFoundException(
            Exception exception, HttpServletRequest httpServletRequest) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(exception.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MandatoryParameterException.class)
    public ResponseEntity<ExceptionResponse> handlerMandatoryParameterException(
            Exception exception, HttpServletRequest httpServletRequest) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(exception.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerConnectionException(
            Exception exception, HttpServletRequest httpServletRequest) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(exception.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
