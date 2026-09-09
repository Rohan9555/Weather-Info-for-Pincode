package com.weather.api.service;

import com.weather.api.client.WeatherClient;
import com.weather.api.entity.WeatherInfo;
import com.weather.api.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository repository;

    @Autowired
    private WeatherClient client;

    public WeatherInfo getWeather(String pincode, LocalDate date) {

        // Step 1: Return from DB if already saved (avoids redundant API call)
        Optional<WeatherInfo> existing = repository.findByPincodeAndDate(pincode, date);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Step 2: Get lat/long from Geocoding API
        Map<String, Double> latLong = client.getLatLong(pincode);
        Double lat = latLong.get("lat");
        Double lon = latLong.get("lon");

        // Step 3: Get weather data from OpenWeather API
        Map<String, Object> weatherData = client.getWeather(lat, lon);

        List weatherList = (List) weatherData.get("weather");
        Map weatherObj = (Map) weatherList.get(0);
        Map main = (Map) weatherData.get("main");

        String weather = (String) weatherObj.get("description");
        Double temp = ((Number) main.get("temp")).doubleValue();

        // Step 4: Save to DB and return
        WeatherInfo info = new WeatherInfo();
        info.setPincode(pincode);
        info.setDate(date);
        info.setLatitude(lat);
        info.setLongitude(lon);
        info.setWeather(weather);
        info.setTemperature(temp);

        return repository.save(info);
    }
}