package com.financeapp.exception;

import com.financeapp.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Runtime Exceptions (your validation errors)
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleRuntimeException(RuntimeException ex) {
        return new ApiResponse("Error", ex.getMessage());
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(Exception ex) {
        return new ApiResponse("Internal Error", ex.getMessage());
    }
}