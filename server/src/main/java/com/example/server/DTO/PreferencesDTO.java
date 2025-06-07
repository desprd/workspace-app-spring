package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PreferencesDTO {
    private Boolean forecastIsAllowed;
    private Boolean newsAreAllowed;
    private String city;
}
