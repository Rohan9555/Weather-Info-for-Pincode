package com.weather.api.controller;

import com.weather.api.entity.WeatherInfo;
import com.weather.api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    // Fetch weather info for a given pincode and date
    @GetMapping
    public ResponseEntity<WeatherInfo> getWeather(
            @RequestParam String pincode,
            @RequestParam String forDate) {

        LocalDate date = LocalDate.parse(forDate);
        WeatherInfo response = service.getWeather(pincode, date);
        return ResponseEntity.ok(response);
    }
}