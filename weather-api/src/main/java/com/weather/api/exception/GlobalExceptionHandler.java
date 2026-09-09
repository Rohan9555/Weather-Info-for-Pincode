package com.weather.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.format.DateTimeParseException;
import java.util.Map;

// Converts exceptions anywhere in the app into clean JSON errors, not raw crashes
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Pincode doesn't exist / geocoding failed
    @ExceptionHandler(PincodeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(PincodeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    // External API call failed (network, timeout, etc.)
    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<Map<String, String>> handleApiFailure(WeatherApiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", ex.getMessage()));
    }

    // Malformed date string in the request
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, String>> handleBadDate(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid date format. Use yyyy-MM-dd."));
    }

    // @Valid failed on WeatherRequest (e.g. bad pincode format)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("Invalid request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", message));
    }

    // Fallback for anything unexpected — never leak a raw stack trace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Something went wrong. Please try again."));
    }
}