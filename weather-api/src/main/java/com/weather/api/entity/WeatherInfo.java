package com.weather.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

// Entity to store weather info for a pincode and date
@Getter
@Setter
@Entity
@Table(name = "weather_info")
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pincode;
    private LocalDate date;

    private Double latitude;
    private Double longitude;

    private String weather;
    private Double temperature;
}