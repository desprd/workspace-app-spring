package com.example.server.utils;

import com.example.server.DTO.WeatherResponseDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ForecastUtils {
    @Cacheable("weatherCache")
    public WeatherResponseDTO getWeather(Double latitude, Double longitude, WebClient client){
        System.out.println("Fetching from API for: " + latitude + ", " + longitude);
        WeatherResponseDTO weather;
        try {
            weather = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https") // important to ensure scheme is correct
                            .host("api.open-meteo.com")
                            .path("/v1/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "temperature_2m", "weather_code")
                            .queryParam("forecast_days", "1")
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherResponseDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Failed request for weather API " + e.getMessage(), e);
        }
        if (weather == null){
            throw new RuntimeException("Failed to get weather report for this coordinates");
        }
        return weather;
    }
}
