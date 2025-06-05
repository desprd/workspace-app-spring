package com.example.server.controller;

import com.example.server.DTO.ProfileDTO;
import com.example.server.service.ProfileInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileInformationController {
    private final ProfileInformationService service;
    @GetMapping("/information")
    public ResponseEntity<?> getProfileInformation(Authentication authentication){
        ProfileDTO res = service.getProfileInformation(authentication);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
