package com.github.library.services;

import com.github.library.controllers.dto.BookDTO;
import com.github.library.entity.Book;
import com.github.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    BookRepository bookRepository;

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
