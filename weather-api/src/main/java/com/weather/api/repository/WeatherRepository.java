package com.weather.api.repository;

import com.weather.api.entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {

    // Fetch saved weather data by pincode and date to avoid redundant API calls
    Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
}
