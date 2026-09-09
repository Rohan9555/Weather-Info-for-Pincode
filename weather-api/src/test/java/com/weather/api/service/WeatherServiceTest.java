package com.weather.api.service;

import com.weather.api.client.WeatherClient;
import com.weather.api.entity.WeatherInfo;
import com.weather.api.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private WeatherRepository repository;

    @Mock
    private WeatherClient client;

    @InjectMocks
    private WeatherService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSavedWeatherIfAlreadyInDB() {
        WeatherInfo saved = new WeatherInfo();
        saved.setPincode("411014");
        saved.setDate(LocalDate.of(2020, 10, 15));
        saved.setWeather("clear sky");
        saved.setTemperature(28.5);

        when(repository.findByPincodeAndDate("411014", LocalDate.of(2020, 10, 15)))
                .thenReturn(Optional.of(saved));

        WeatherInfo result = service.getWeather("411014", LocalDate.of(2020, 10, 15));

        assertEquals("clear sky", result.getWeather());
        verify(client, never()).getLatLong(any());
        verify(client, never()).getWeather(any(), any());
    }

    @Test
    void shouldFetchFromAPIIfNotInDB() {
        when(repository.findByPincodeAndDate(any(), any()))
                .thenReturn(Optional.empty());

        when(client.getLatLong("411014"))
                .thenReturn(Map.of("lat", 18.5204, "lon", 73.8567));

        when(client.getWeather(18.5204, 73.8567))
                .thenReturn(Map.of(
                        "weather", List.of(Map.of("description", "light rain")),
                        "main", Map.of("temp", 26.0)
                ));

        WeatherInfo mockSaved = new WeatherInfo();
        mockSaved.setPincode("411014");
        mockSaved.setWeather("light rain");
        mockSaved.setTemperature(26.0);

        when(repository.save(any())).thenReturn(mockSaved);

        WeatherInfo result = service.getWeather("411014", LocalDate.of(2020, 10, 15));

        assertEquals("light rain", result.getWeather());
        verify(client, times(1)).getLatLong("411014");
        verify(client, times(1)).getWeather(18.5204, 73.8567);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldSaveLatLongCorrectly() {
        when(repository.findByPincodeAndDate(any(), any()))
                .thenReturn(Optional.empty());

        when(client.getLatLong("411014"))
                .thenReturn(Map.of("lat", 18.5204, "lon", 73.8567));

        when(client.getWeather(18.5204, 73.8567))
                .thenReturn(Map.of(
                        "weather", List.of(Map.of("description", "sunny")),
                        "main", Map.of("temp", 30.0)
                ));

        WeatherInfo mockSaved = new WeatherInfo();
        mockSaved.setLatitude(18.5204);
        mockSaved.setLongitude(73.8567);

        when(repository.save(any())).thenReturn(mockSaved);

        WeatherInfo result = service.getWeather("411014", LocalDate.of(2020, 10, 15));

        assertEquals(18.5204, result.getLatitude());
        assertEquals(73.8567, result.getLongitude());
    }

    @Test
    void shouldNotCallAPIWhenDataExistsInDB() {
        WeatherInfo saved = new WeatherInfo();
        saved.setPincode("411014");
        saved.setDate(LocalDate.of(2020, 10, 15));

        when(repository.findByPincodeAndDate("411014", LocalDate.of(2020, 10, 15)))
                .thenReturn(Optional.of(saved));

        service.getWeather("411014", LocalDate.of(2020, 10, 15));

        verifyNoInteractions(client);
    }
}