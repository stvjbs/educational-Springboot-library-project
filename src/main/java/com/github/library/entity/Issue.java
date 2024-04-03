package com.github.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Reader reader;

    @ManyToOne
    @JoinColumn
    private Book book;

    @Column(name = "Issued_at")
    private LocalDateTime issuedAt = LocalDateTime.now();
    @Column(name = "Returned_at")
    private LocalDateTime returnedAt = null;
}
