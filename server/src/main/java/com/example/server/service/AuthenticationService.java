package com.example.server.service;

import com.example.server.DTO.LoginRequestDTO;
import com.example.server.DTO.RegistrationRequestDTO;
import com.example.server.DTO.UserAuthDTO;
import com.example.server.model.Role;
import com.example.server.model.User;
import com.example.server.model.UserPrinciple;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        repository.save(user);
        var token = jwtService.generateToken(new UserPrinciple(user));
        return UserAuthDTO
                .builder()
                .token(token)
                .username(username)
                .email(email)
                .build();
    }

    public UserAuthDTO login(LoginRequestDTO req){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        var user = repository.findByUsername(req.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User with such username was not found"));
        var token = jwtService.generateToken(new UserPrinciple(user));
        return UserAuthDTO
                .builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
