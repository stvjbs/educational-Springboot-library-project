package com.github.library.services;

import com.github.library.dto.BookDTO;
import com.github.library.entity.Book;
import com.github.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).get();
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getName());
        bookRepository.save(book);
        return book;
    }

    public Book deleteBook(long bookId) {
        Book book = bookRepository.findById(bookId).get();
        bookRepository.delete(book);
        return book;
    }
}
