package com.github.library.dto;

import com.github.library.entity.Book;
import com.github.library.entity.Reader;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueDTO {
    @NotNull
    private Reader reader;
    @NotNull
    private Book book;
}
