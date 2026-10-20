package com.study_tracker.ayush.controller;

import com.study_tracker.ayush.auth.PinAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, Object> errors = new LinkedHashMap<>();

        errors.put("status", 400);
        errors.put("error", "Validation Failed");

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        for (FieldError error :
                exception.getBindingResult().getFieldErrors()) {

            fieldErrors.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        errors.put("fieldErrors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestBody(
            HttpMessageNotReadableException exception) {

        Map<String, Object> error = new LinkedHashMap<>();

        error.put("status", 400);
        error.put("error", "Invalid request body");
        error.put(
                "message",
                "Check that the JSON is valid and the field types are correct."
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(PinAuthService.InvalidPinException.class)
    public ResponseEntity<java.util.Map<String, Object>> handleInvalidPin(
            PinAuthService.InvalidPinException exception) {

        java.util.Map<String, Object> body = new java.util.LinkedHashMap<>();

        body.put("status", 401);
        body.put("error", "Unauthorized");
        body.put("message", "Invalid PIN");

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }
}