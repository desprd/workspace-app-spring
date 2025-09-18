package com.example.server.controller;


import com.example.server.DTO.NoteDTO;
import com.example.server.DTO.NoteRequestDTO;
import com.example.server.model.Note;
import com.example.server.model.Status;
import com.example.server.repository.UserRepository;
import com.example.server.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteControllerTest {
    @MockitoBean
    private NoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "User", authorities = "USER")
    void addNoteTest_return201_andResponseEntity() throws Exception{
        NoteDTO note = NoteDTO
                .builder()
                .title("Title")
                .content("Content")
                .status(Status.ACTIVE)
                .howLongAgoCreated("Today")
                .build();
        when(noteService.addNote(any(Authentication.class), any(NoteRequestDTO.class))).thenReturn(note);
        mockMvc.perform(post("/api/note/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title": "Title", "content": "Content"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.howLongAgoCreated").value("Today"));
    }



}
