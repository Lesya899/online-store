package com.example.notificationservice.exception;


import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

public class FallBackException {

    public static void generateExceptionByCause(Throwable cause) throws Exception {
        if (cause instanceof FeignException.FeignClientException.NotFound) {
            throw new NotFoundException(cause.getMessage());
        } else if (cause instanceof FeignException.FeignClientException.BadRequest) {
            throw new BadRequestException(cause.getMessage());
        } else {
            throw new Exception(cause.getMessage());
        }
    }
}

