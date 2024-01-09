package org.nikdev.financialoperations.exception;

import org.nikdev.financialoperations.dto.response.ExceptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionFinOperationsHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptResponse handleUserNotFoundException(Exception exception) {
        return new ExceptResponse("BAD_REQUEST", exception.getMessage());
    }
}
