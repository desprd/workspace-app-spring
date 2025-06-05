package com.example.server.service;

import com.example.server.DTO.ProfileDTO;
import com.example.server.DTO.ProfileRequestDTO;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileInformationService {
    private final UserRepository repository;
    private final CloudinaryServiceImpl cloudinaryService;
    public ProfileDTO getProfileInformation(Authentication authentication){
        var user = getUser(authentication);
        return getProfileDTO(user);
    }
    public ProfileDTO updateProfileInformation(Authentication authentication, ProfileRequestDTO req){
        var user = getUser(authentication);
        if (req.getEmail().isEmpty() || req.getEmail()== null){
            throw new RuntimeException("Email address can't be empty");
        }
        user.setEmail(req.getEmail());
        user.getProfile().setCompanyName(req.getCompanyName());
        user.getProfile().setJobTitle(req.getJobTitle());
        if (req.getProfilePictureFile() != null && !req.getProfilePictureFile().isEmpty()){
            user.getProfile().setProfilePictureURL(cloudinaryService.uploadFile(req.getProfilePictureFile(), "workspace-app/profile-pictures"));
        }
        repository.save(user);
        return getProfileDTO(user);
    }
    private User getUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not exists"));
    }
    private ProfileDTO getProfileDTO(User user){
        return ProfileDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePictureURL(user.getProfile().getProfilePictureURL())
                .companyName(user.getProfile().getCompanyName())
                .jobTitle(user.getProfile().getJobTitle())
                .build();
    }
}
