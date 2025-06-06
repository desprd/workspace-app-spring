package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AllNotesDTO {
    private List<NoteDTO> allNotes;
    private String defaultTitle;
}
