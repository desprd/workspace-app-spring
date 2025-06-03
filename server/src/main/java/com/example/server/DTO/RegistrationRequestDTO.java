package com.example.server.DTO;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegistrationRequestDTO {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    private String password;
}
