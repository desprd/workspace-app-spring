package com.example.server.service;

import com.example.server.DTO.ProfileDTO;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileInformationService {
    private final UserRepository repository;
    public ProfileDTO getProfileInformation(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var user = repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username now exists"));
        return ProfileDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePictureURL(user.getProfile().getProfilePictureURL())
                .companyName(user.getProfile().getCompanyName())
                .jobTitle(user.getProfile().getJobTitle())
                .build();
    }
}
