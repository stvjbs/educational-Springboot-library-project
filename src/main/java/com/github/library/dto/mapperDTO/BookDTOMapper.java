package com.github.library.dto.mapperDTO;

import com.github.library.dto.BookDTO;
import com.github.library.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDTOMapper {
    public Book mapToBook(BookDTO bookDTO) {
        return new Book(bookDTO.getName());
    }

    public BookDTO mapToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO(book.getName());
        bookDTO.setId(book.getId());
        return bookDTO;
    }

    public List<BookDTO> mapToListDTO(List<Book> list) {
        return list.stream().map(this::mapToBookDTO).toList();
    }
}
