package com.weather.api.exception;

// Thrown when the Geocoding API can't resolve a pincode
public class PincodeNotFoundException extends RuntimeException {
    public PincodeNotFoundException(String pincode) {
        super("No location found for pincode: " + pincode);
    }
}