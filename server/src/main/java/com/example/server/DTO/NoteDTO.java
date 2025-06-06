package com.example.server.DTO;

import com.example.server.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NoteDTO {
    private Integer id;
    private String title;
    private String content;
    private Status status;
    private String howLongAgoCreated;
}
