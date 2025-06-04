package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserAuthDTO {
    private String token;
    private String username;
    public UserAuthDTO(String username){
        this.username = username;
    }
}
