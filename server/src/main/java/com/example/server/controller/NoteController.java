package com.example.server.controller;

import com.example.server.DTO.AllNotesDTO;
import com.example.server.DTO.NoteDTO;
import com.example.server.DTO.NoteRequestDTO;
import com.example.server.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
@CrossOrigin
public class NoteController {
    private final NoteService service;
    @PostMapping("/add")
    public ResponseEntity<?> addNote(Authentication authentication,
                                     @RequestBody NoteRequestDTO note){
        try {
            NoteDTO newNote = service.addNote(authentication, note);
            return new ResponseEntity<>(newNote, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/get")
    public ResponseEntity<?> getAllNotes(Authentication authentication){
        try {
            AllNotesDTO notes = service.getAllNotes(authentication);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?> changeNoteStatus(@PathVariable int id,
                                              Authentication authentication){
        try {
            service.changeNoteStatus(id, authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
