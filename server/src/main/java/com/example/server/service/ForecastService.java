package com.example.server.service;

import com.example.server.DTO.WeatherDTO;
import com.example.server.DTO.WeatherResponseDTO;
import com.example.server.repository.UserRepository;
import com.example.server.utils.ForecastUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastService {
    private final WebClient client;
    private final UserRepository repository;
    private final ForecastUtils utils;
    public WeatherResponseDTO getForecast(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var user = repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not exists"));
        return utils.getWeather(user.getPreferences().getLatitude(), user.getPreferences().getLongitude(), client);
    }
    public List<Double> getCoordinates(String cityName) {
        JsonNode jsonNode;
        try {
            jsonNode = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https") // important to ensure scheme is correct
                            .host("geocoding-api.open-meteo.com")
                            .path("/v1/search")
                            .queryParam("name", cityName)
                            .queryParam("count", 1)
                            .queryParam("language", "en")
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Failed converting city name to coordinates " + e.getMessage(), e);
        }

        List<Double> coordinates = new ArrayList<>();

        JsonNode firstResult = jsonNode.path("results").path(0);

        if (!firstResult.isMissingNode()) {
            coordinates.add(firstResult.path("latitude").asDouble());
            coordinates.add(firstResult.path("longitude").asDouble());
        }else {
            throw new RuntimeException("No coordinates found for " + cityName);
        }
        return coordinates;
    }
}
