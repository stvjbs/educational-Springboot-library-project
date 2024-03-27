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

    @ManyToOne
    @JoinColumn
    private final Reader reader;

    @ManyToOne
    @JoinColumn
    private final Book book;

    @Column(name = "Issued_at")
    private LocalDateTime issuedAt = LocalDateTime.now();
    @Column(name = "Returned_at")
    private LocalDateTime returnedAt = null;
}
