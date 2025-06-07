package com.example.server.repository;


import com.example.server.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<Preferences, Integer> {
}
