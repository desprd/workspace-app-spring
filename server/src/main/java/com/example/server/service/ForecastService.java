package com.example.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastService {
    private final WebClient client;
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
