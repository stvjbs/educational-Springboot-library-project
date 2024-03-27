package com.github.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {
    @NotBlank
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;
}