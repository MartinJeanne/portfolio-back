package com.martinjeanne.portfolio_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        StringBuilder errorMsg = new StringBuilder("Missing fields: ");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMsg.append(error.getField()).append(", ");
        }
        errorMsg.delete(errorMsg.lastIndexOf(","), errorMsg.length());
        errors.put("error", errorMsg.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Other errors
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Unhandled error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}