package com.example.server.service;

import com.example.server.DTO.LoginRequestDTO;
import com.example.server.DTO.RegistrationRequestDTO;
import com.example.server.DTO.UserAuthDTO;
import com.example.server.model.*;
import com.example.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void setUpUser(){
        var user = User.builder()
                .username("User")
                .email("user@email")
                .password(encoder.encode("password"))
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
        user.setPreferences(preferences);
        user.setProfile(profile);
        repository.save(user);
    }

    @Test
    void registerTest_returnUserAuthDTO(){
        RegistrationRequestDTO registrationRequestDTO = RegistrationRequestDTO
                .builder()
                .username("Aleg")
                .email("aleg@qwe")
                .password("12345678")
                .build();
        UserAuthDTO authDTO = authenticationService.register(registrationRequestDTO);
        assertNotNull(authDTO);
        assertEquals("Aleg", authDTO.getUsername());
    }

    @Test
    void loginTest_returnUserAuthDTO(){
        LoginRequestDTO request = LoginRequestDTO
                .builder()
                .username("User")
                .password("password")
                .build();
        UserAuthDTO authDTO = authenticationService.login(request);
        assertNotNull(authDTO);
        assertEquals("User", authDTO.getUsername());
    }

    @Test
    void loginTest_returnException(){
        LoginRequestDTO request = LoginRequestDTO
                .builder()
                .username("User")
                .password("incorrectPassword")
                .build();
        assertThrows(RuntimeException.class, () -> authenticationService.login(request));
    }

    @Test
    @WithMockUser(username = "User", authorities = "USER")
    void getUserTest_returnPersistedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authenticationService.getUser(auth);
        assertEquals("User", user.getUsername());
    }

    @Test
    @WithMockUser(username = "AnotherUser", authorities = "USER")
    void getUserTest_throwsUsernameNotFoundException(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.getUser(auth));
    }
}
