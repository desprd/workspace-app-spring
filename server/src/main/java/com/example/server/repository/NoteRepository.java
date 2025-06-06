package com.example.server.repository;

import com.example.server.model.Note;
import com.example.server.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
