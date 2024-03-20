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
        return bookRepository.findAllBooks();
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getName());
        bookRepository.addBook(book);
        return book;
    }

    public Book deleteBook(long bookId) {
        return bookRepository.deleteBook(bookId);
    }
}
