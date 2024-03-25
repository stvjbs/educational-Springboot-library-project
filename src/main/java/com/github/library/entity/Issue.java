package com.github.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Id_reader")
    private final long idReader;
    @Column(name = "Id_book")
    private final long idBook;
    @Column(name = "Issued_at")
    private LocalDateTime issuedAt = LocalDateTime.now();
    @Column(name = "Returned_at")
    private LocalDateTime returnedAt = null;
}
