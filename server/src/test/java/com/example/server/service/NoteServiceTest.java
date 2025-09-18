package com.example.server.service;

import com.example.server.DTO.AllNotesDTO;
import com.example.server.DTO.NoteDTO;
import com.example.server.DTO.NoteRequestDTO;
import com.example.server.model.*;
import com.example.server.repository.NoteRepository;
import com.example.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class NoteServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    private User user;

    private Note note;

    private Authentication auth;

    @BeforeEach
    void setUp(){
        note = Note
                .builder()
                .id(1)
                .title("Title")
                .content("Content")
                .status(Status.ACTIVE)
                .createdOn(LocalDate.now())
                .build();
        user = User.builder()
                .username("User")
                .email("user@email")
                .password("password")
                .role(Role.USER)
                .notes(new ArrayList<>(Arrays.asList(note)))
                .build();
        var profile = Profile.builder()
                .companyName(null)
                .jobTitle(null)
                .profilePictureURL("https://res.cloudinary.com/diha4exbb/image/upload/v1749071901/user_ubtddi.png")
                .user(user)
                .build();
        var preferences = Preferences.builder()
                .forecastIsAllowed(false)
                .newsAreAllowed(false)
                .city(null)
                .user(user)
                .build();
        user.setPreferences(preferences);
        user.setProfile(profile);
        UserDetails principal = org.springframework.security.core.userdetails.User
                .withUsername("User")
                .password("password")
                .authorities("USER")
                .build();
        auth = new UsernamePasswordAuthenticationToken(
                principal, null, principal.getAuthorities()
        );
        when(userRepository.findByUsername("User")).thenReturn(Optional.of(user));
    }

    @Test
    void addNoteTest_returnNoteDTO(){
        when(noteRepository.save(any(Note.class))).thenAnswer(inv -> inv.getArgument(0));
        NoteRequestDTO request = new NoteRequestDTO("Title2", "Content");
        NoteDTO note = noteService.addNote(auth, request);
        assertNotNull(note);
        assertEquals("Title2", note.getTitle());
    }

    @Test
    void deleteNoteTest_void(){
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        noteService.deleteNote(1, auth);
        assertEquals(0, user.getNotes().size());
    }

    @Test
    void getAllNotesTest_returnAllNotesDTO(){
        AllNotesDTO notes = noteService.getAllNotes(auth);
        assertNotNull(notes);
        assertEquals(1, notes.getAllNotes().size());
    }

    @Test
    void updateNoteTest_void(){
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        NoteRequestDTO request = new NoteRequestDTO("Updated", "Updated");
        noteService.updateNote(1, request, auth);
        assertEquals("Updated", note.getTitle());
    }

}

