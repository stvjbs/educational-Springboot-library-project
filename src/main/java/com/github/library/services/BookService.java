package com.github.library.services;

import com.github.library.dto.BookDTO;
import com.github.library.dto.mapperDTO.BookDTOMapper;
import com.github.library.entity.Book;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDTOMapper bookDTOMapper;
    public List<BookDTO> getAllBooks() {
        List<BookDTO> list = new ArrayList<>();
        bookRepository.findAll().forEach(x->list.add(bookDTOMapper.mapToBookDTO(x)));
        return list;
    }

    public BookDTO getBookById(long id) {
        Book thisBook = bookRepository.findById(id).orElseThrow(NotFoundEntityException::new);
        return bookDTOMapper.mapToBookDTO(thisBook);
    }

    public BookDTO addBook(BookDTO bookDTO) {
        Book book = bookDTOMapper.mapToBook(bookDTO);
        bookRepository.save(book);
        return bookDTO;
    }

    public void deleteBook(long bookId) {
       bookRepository.deleteBookById(bookId).orElseThrow(NotFoundEntityException::new);
    }
}
