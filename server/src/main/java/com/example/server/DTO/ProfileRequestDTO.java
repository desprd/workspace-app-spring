package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProfileRequestDTO {
    private String email;
    private String jobTitle;
    private String companyName;
    private MultipartFile profilePictureFile;
}
