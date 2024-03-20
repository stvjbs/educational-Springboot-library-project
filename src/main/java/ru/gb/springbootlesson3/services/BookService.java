package ru.gb.springbootlesson3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.controllers.dto.BookDTO;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.repository.BookRepository;

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
