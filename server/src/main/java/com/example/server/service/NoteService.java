package com.example.server.service;

import com.example.server.DTO.AllNotesDTO;
import com.example.server.DTO.DashboardNotesDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.stream.Collectors;

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
                      .id(note.getId())
                      .title(req.getTitle())
                      .content(req.getContent())
                      .status(Status.ACTIVE)
                      .howLongAgoCreated("Today")
                      .build();
    }

    public AllNotesDTO getAllNotes(Authentication authentication){
        var user = getUser(authentication);
        List<Note> userNotes = user.getNotes();
        String defaultTitle = getDefaultTitle(userNotes);
        List<NoteDTO> noteDTOList = userNotes.stream().map(note -> NoteDTO.builder()
                                                                          .id(note.getId())
                                                                          .title(note.getTitle())
                                                                          .content(note.getContent())
                                                                          .status(note.getStatus())
                                                                          .howLongAgoCreated(getHowLongAgoCreated(note.getCreatedOn()))
                                                                          .build())
                                                       .collect(Collectors.toList());
        return AllNotesDTO.builder()
                .allNotes(noteDTOList)
                .defaultTitle(defaultTitle)
                .build();
    }
    public DashboardNotesDTO getTodayNotes(Authentication authentication){
        List<NoteDTO> noteDTOList = getAllNotes(authentication)
                .getAllNotes()
                .stream()
                .filter(note -> note
                        .getHowLongAgoCreated()
                        .equals("Today"))
                .toList();
        String timeOfDay = getTimeOFDay();
        return DashboardNotesDTO.builder()
                .todayNotes(noteDTOList)
                .timeOfDay(timeOfDay)
                .build();
    }
    @Transactional
    public void changeNoteStatus(int id, Authentication authentication){
        try {
            var user = getUser(authentication);
            Note note = noteRepository.getReferenceById(id);
            if (!user.getNotes().contains(note)){
                throw new RuntimeException("User has no access for note with id " + id);
            }
            note.setStatus(Status.DONE);
        }catch (Exception e){
            throw new RuntimeException("Couldn't change status for note with id " + id);
        }
    }
    @Transactional
    public void deleteNote(int id, Authentication authentication){
        try {
            var user = getUser(authentication);
            Note note = noteRepository.findById(id).orElseThrow(()-> new RuntimeException("Note was not found "));
            if (!user.getNotes().contains(note)){
                throw new RuntimeException("User has no access for note with id " + id);
            }
            user.getNotes().remove(note);
        }catch (Exception e){
            throw new RuntimeException("Couldn't delete note with id " + id);
        }
    }

    @Transactional
    public void updateNote(int id, NoteRequestDTO request, Authentication authentication){
        var user = getUser(authentication);
        Note note = noteRepository.findById(id).orElseThrow(()-> new RuntimeException("Note was not found "));
        if (!user.getNotes().contains(note)){
            throw new RuntimeException("User has no access for note with id " + id);
        }
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
    }
    private User getUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not exists"));
    }
    private String getDefaultTitle(List<Note> userNotes){
        boolean hasDefault = userNotes
                .stream()
                .anyMatch(note -> note.getTitle()
                        .equals("New Note"));
        if(!hasDefault){
            return "New Note";
        }
        int i = 1;
        while (true){
            String defaultTitle = "New Note " + i;
            hasDefault = userNotes
                    .stream()
                    .anyMatch(note -> note.getTitle()
                            .equals(defaultTitle));
            if (!hasDefault){
                return defaultTitle;
            }
            i++;
        }
    }
    private String getHowLongAgoCreated(LocalDate createdOn){
        long difference = ChronoUnit.DAYS.between(createdOn, LocalDate.now());
        if (difference == 0){
            return "Today";
        }else if (difference == 1){
            return "1 day ago";
        }else if (difference > 1 && difference < 7){
            return (difference + " days ago");
        }else if(difference == 7){
            return "Week ago";
        }else if (difference > 7){
            return "More than week ago";
        }
        return "";
    }
    private String getTimeOFDay(){
        long hour = LocalTime.now().getHour();
        if (hour >= 5 && hour < 12) {
            return "morning";
        } else if (hour >= 12 && hour < 17) {
            return "afternoon";
        } else{
            return "evening";
        }
    }
}
