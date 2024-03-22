package com.github.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idReader;
    private long idBook;
    private LocalDateTime issuedAt;
    private LocalDateTime returnedAt;

    public Issue(long idReader, long idBook) {
        this.idBook = idBook;
        this.idReader = idReader;
        issuedAt = LocalDateTime.now();
        returnedAt = null;
    }
}
