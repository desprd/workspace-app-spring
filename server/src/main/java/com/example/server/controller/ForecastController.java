package com.example.server.controller;

import com.example.server.DTO.WeatherDTO;
import com.example.server.DTO.WeatherResponseDTO;
import com.example.server.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forecast")
@RequiredArgsConstructor
public class ForecastController {
    private final ForecastService service;
    @GetMapping("/get")
    public ResponseEntity<?> getForecast(Authentication authentication){
        try {
            WeatherResponseDTO daily = service.getForecast(authentication);
            return new ResponseEntity<>(daily, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
