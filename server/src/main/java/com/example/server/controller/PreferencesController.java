package com.example.server.controller;

import com.example.server.DTO.PreferencesDTO;
import com.example.server.service.PreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
@CrossOrigin
public class PreferencesController {
    private final PreferencesService service;
    @PutMapping("/update")
    public ResponseEntity<?> updatePreferences(@RequestBody PreferencesDTO preferencesDTO,
                                               Authentication authentication){
        try {
            service.updatePreferences(preferencesDTO, authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
