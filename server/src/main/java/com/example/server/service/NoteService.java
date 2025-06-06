package com.example.server.service;

import com.example.server.DTO.AllNotesDTO;
import com.example.server.DTO.NoteDTO;
import com.example.server.DTO.NoteRequestDTO;
import com.example.server.model.Note;
import com.example.server.model.Status;
import com.example.server.model.User;
import com.example.server.repository.NoteRepository;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final UserRepository repository;
    private final NoteRepository noteRepository;
    public NoteDTO addNote(Authentication authentication, NoteRequestDTO req){
        var user = getUser(authentication);
        List<Note> userNotes = user.getNotes();
        boolean hasSameTitle = userNotes
                .stream()
                .anyMatch(note -> note.getTitle()
                        .equals(req.getTitle()));
        if (hasSameTitle){
            throw new RuntimeException("You already have note with this title");
        }
        Note note = Note.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .status(Status.ACTIVE)
                .user(user)
                .build();
        noteRepository.save(note);
        return NoteDTO.builder()
                      .title(req.getTitle())
                      .content(req.getContent())
                      .status(Status.ACTIVE)
                      .build();
    }

    public AllNotesDTO getAllNotes(Authentication authentication){
        var user = getUser(authentication);
        List<Note> userNotes = user.getNotes();
//        String defaultTitle =
    }
    private User getUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not exists"));
    }
}
