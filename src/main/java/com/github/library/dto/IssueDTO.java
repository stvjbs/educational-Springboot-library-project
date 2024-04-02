package com.github.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class IssueDTO {
    @NotNull
    private ReaderDTO readerDTO;
    @NotNull
    private BookDTO bookDTO;

    private LocalDateTime issuedAt;
    private LocalDateTime returnedAt;
    private final long id;
}
