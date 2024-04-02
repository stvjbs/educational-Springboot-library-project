package com.github.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookDTO {
    @NotBlank
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private final String name;

    private long id;
}