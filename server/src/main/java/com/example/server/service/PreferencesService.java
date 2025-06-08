package com.example.server.service;

import com.example.server.DTO.PreferencesDTO;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferencesService {
    private final UserRepository repository;
    private final ForecastService service;
    @Transactional
    public void updatePreferences(PreferencesDTO preferencesDTO,
                                  Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var user = repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not exists"));
        user.getPreferences().setNewsAreAllowed(preferencesDTO.getNewsAreAllowed());
        if (preferencesDTO.getForecastIsAllowed()){
            user.getPreferences().setForecastIsAllowed(true);
            try {
                List<Double> coordinates = service.getCoordinates(preferencesDTO.getCity());
                user.getPreferences().setCity(preferencesDTO.getCity());
                user.getPreferences().setLatitude(coordinates.get(0));
                user.getPreferences().setLongitude(coordinates.get(1));
            }catch (Exception e){
                throw new RuntimeException("Incorrect city name ");
            }
        }else {
            user.getPreferences().setForecastIsAllowed(false);
        }
    }
}
