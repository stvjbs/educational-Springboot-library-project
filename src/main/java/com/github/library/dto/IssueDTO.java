package com.github.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueDTO {
    @NotBlank
    private long readerId;
    @NotBlank
    private long bookId;
}
