package com.weather.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

// Handles all external OpenWeather API calls
@Component
public class WeatherClient {

    @Value("${openweather.api.key}")
    private String apiKey;

    // Converts pincode to lat/long using OpenWeather Geocoding API
    public Map<String, Double> getLatLong(String pincode) {
        String url = "http://api.openweathermap.org/geo/1.0/zip?zip="
                + pincode + ",IN&appid=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForObject(url, Map.class);

        Map<String, Double> result = new HashMap<>();
        result.put("lat", (Double) response.get("lat"));
        result.put("lon", (Double) response.get("lon"));

        return result;
    }

    // Fetches weather data for given coordinates
    public Map<String, Object> getWeather(Double lat, Double lon) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="
                + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Map.class);
    }
}