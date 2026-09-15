package com.sanjay.healthcare_platform.common.exception;

import com.sanjay.healthcare_platform.common.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildError(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        return buildError(ex.getMessage(), HttpStatus.CONFLICT, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(err -> err.getDefaultMessage())
            .orElse("Validation failed");
        return buildError(message, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    private ResponseEntity<ApiError> buildError(String message, HttpStatus status, String path) {
        ApiError error = new ApiError(
            Instant.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path
        );
        return ResponseEntity.status(status).body(error);
    }
}
