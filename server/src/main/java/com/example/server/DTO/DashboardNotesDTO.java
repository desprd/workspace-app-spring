package com.example.server.DTO;

import com.example.server.model.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DashboardNotesDTO {
    private List<NoteDTO> todayNotes;
    private String timeOfDay;
}
