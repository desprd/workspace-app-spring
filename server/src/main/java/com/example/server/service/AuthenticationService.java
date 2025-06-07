package com.example.server.service;

import com.example.server.DTO.LoginRequestDTO;
import com.example.server.DTO.PreferencesDTO;
import com.example.server.DTO.RegistrationRequestDTO;
import com.example.server.DTO.UserAuthDTO;
import com.example.server.config.JwtAuthenticationFilter;
import com.example.server.model.*;
import com.example.server.repository.ProfileRepository;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public UserAuthDTO register(RegistrationRequestDTO req){
        String username = req.getUsername();
        String email = req.getEmail();
        if (repository.existsByUsername(username)){
            throw new RuntimeException("User with such username already exists");
        }
        var user = User.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();
        var profile = Profile.builder()
                        .companyName(null)
                        .jobTitle(null)
                        .profilePictureURL("https://res.cloudinary.com/diha4exbb/image/upload/v1749071901/user_ubtddi.png")
                        .user(user)
                        .build();
        var preferences = Preferences.builder()
                        .forecastIsAllowed(false)
                        .newsAreAllowed(false)
                        .city(null)
                        .user(user)
                        .build();
        user.setProfile(profile);
        user.setPreferences(preferences);
        repository.save(user);
        var token = jwtService.generateToken(new UserPrinciple(user));
        var preferencesDTO = getPreferencesDTO(preferences);
        return UserAuthDTO
                .builder()
                .token(token)
                .username(username)
                .preferences(preferencesDTO)
                .build();
    }

    public UserAuthDTO login(LoginRequestDTO req){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );
        }catch (Exception e){
            throw new RuntimeException("Username or password are incorrect");
        }
        var user = repository.findByUsername(req.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User with such username was not found"));
        var token = jwtService.generateToken(new UserPrinciple(user));
        var preferencesDTO = getPreferencesDTO(user.getPreferences());
        return UserAuthDTO
                .builder()
                .token(token)
                .username(user.getUsername())
                .preferences(preferencesDTO)
                .build();
    }
    public User getUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not exists"));
    }
    public PreferencesDTO getPreferencesDTO(Preferences preferences){
        return PreferencesDTO.builder()
                .newsAreAllowed(preferences.getNewsAreAllowed())
                .forecastIsAllowed(preferences.getForecastIsAllowed())
                .city(preferences.getCity())
                .build();
    }

}
