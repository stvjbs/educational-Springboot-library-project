package com.github.library.dto.mapperDTO;

import com.github.library.dto.BookDTO;
import com.github.library.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookDTOMapper {
    public Book mapToBook(BookDTO bookDTO) {
        return new Book(bookDTO.getName());
    }

    public BookDTO mapToBookDTO(Book book) {
        return new BookDTO(book.getName());
    }
}
