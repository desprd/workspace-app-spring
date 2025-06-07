package com.example.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String title;
    private String content;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @UpdateTimestamp
    private LocalDate createdOn;
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;
}
