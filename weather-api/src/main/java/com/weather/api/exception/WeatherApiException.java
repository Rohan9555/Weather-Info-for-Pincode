package com.weather.api.exception;

// Thrown when an external API call fails unexpectedly
public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String message, Throwable cause) {
        super(message, cause);
    }
}