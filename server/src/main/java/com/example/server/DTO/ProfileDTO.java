package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProfileDTO {
    private String username;
    private String email;
    private String jobTitle;
    private String companyName;
    private String profilePictureURL;
}
