package com.example.server.controller;

import com.example.server.DTO.PreferencesDTO;
import com.example.server.DTO.RegistrationRequestDTO;
import com.example.server.DTO.UserAuthDTO;
import com.example.server.model.Preferences;
import com.example.server.model.User;
import com.example.server.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {
    @MockitoBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerTest_return201_andResponseEntity() throws Exception{
        PreferencesDTO preferences = new PreferencesDTO(true, true, "city");
        UserAuthDTO userAuthDTO = new UserAuthDTO("token", "username", preferences);
        when(authenticationService.register(any(RegistrationRequestDTO.class))).thenReturn(userAuthDTO);
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username": "username", "email": "email", "password":"password"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.preferences").value(preferences));
    }

    @Test
    void registerFailTest_return400_andResponseEntity() throws Exception{
        PreferencesDTO preferences = new PreferencesDTO(true, true, "city");
        when(authenticationService.register(any(RegistrationRequestDTO.class))).thenThrow(new RuntimeException("Registration failed"));
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"username": "username", "email": "email", "password":"password"}
                        """))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Registration failed"));

    }

    @Test
    @WithMockUser(username = "User", authorities = "USER")
    void verifyTest_return200_andResponseEntity() throws Exception{
        Preferences preferences = Preferences
                .builder()
                .newsAreAllowed(true)
                .forecastIsAllowed(true)
                .city("city")
                .latitude(0.0)
                .longitude(0.0)
                .build();
        User user = User
                .builder()
                .username("User")
                .preferences(preferences)
                .build();
        when(authenticationService.getUser(any(Authentication.class))).thenReturn(user);
        when(authenticationService.getPreferencesDTO(preferences)).thenReturn(new PreferencesDTO(true, true, "city"));
        mockMvc.perform(get("/api/auth/verify"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("User"));
    }
}
