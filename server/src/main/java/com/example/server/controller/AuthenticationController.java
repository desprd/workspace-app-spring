package com.example.server.controller;

import com.example.server.DTO.LoginRequestDTO;
import com.example.server.DTO.RegistrationRequestDTO;
import com.example.server.DTO.UserAuthDTO;
import com.example.server.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO req){
        try {
            UserAuthDTO res = service.register(req);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CREATED);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req){
        try {
            UserAuthDTO res = service.login(req);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}