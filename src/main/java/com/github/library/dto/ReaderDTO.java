package com.github.library.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ReaderDTO {
    @NonNull
    @Size(min = 3, message = "Name must exists at least 3 characters long.")
    private String name;

    private long id;
}

