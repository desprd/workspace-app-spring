package com.example.server.controller;

import com.example.server.DTO.ProfileDTO;
import com.example.server.DTO.ProfileRequestDTO;
import com.example.server.service.CloudinaryServiceImpl;
import com.example.server.service.ProfileInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileInformationController {
    private final ProfileInformationService service;
    private final CloudinaryServiceImpl cloudService;
    @GetMapping("/information")
    public ResponseEntity<?> getProfileInformation(Authentication authentication){
        ProfileDTO res = service.getProfileInformation(authentication);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfileInformation(Authentication authentication, @ModelAttribute ProfileRequestDTO req){
        try {
            ProfileDTO profile = service.updateProfileInformation(authentication, req);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
