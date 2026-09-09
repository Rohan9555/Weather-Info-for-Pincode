package com.weather.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

// DTO for incoming weather request parameters
@Getter
@Setter
public class WeatherRequest {
    private String pincode;
    private LocalDate forDate;
}