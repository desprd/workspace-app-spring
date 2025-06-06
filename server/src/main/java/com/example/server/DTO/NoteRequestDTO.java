package com.example.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NoteRequestDTO {
    private String title;
    private String content;
}
