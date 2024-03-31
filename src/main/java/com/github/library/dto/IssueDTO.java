package com.github.library.dto;

import com.github.library.entity.Book;
import com.github.library.entity.Reader;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class IssueDTO {
    @NotNull
    private Reader reader;
    @NotNull
    private Book book;

    private LocalDateTime issuedAt;
    private LocalDateTime returnedAt;
    private long id;
}
