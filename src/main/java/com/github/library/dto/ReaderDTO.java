package com.github.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReaderDTO {
    @NotBlank
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private final String name;

    private long id;

}
