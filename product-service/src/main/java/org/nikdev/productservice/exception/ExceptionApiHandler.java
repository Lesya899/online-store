package org.nikdev.productservice.exception;


import org.nikdev.productservice.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notFoundException(Exception exception) {
        return new ExceptionResponse("BAD_REQUEST", exception.getMessage());
    }
}
