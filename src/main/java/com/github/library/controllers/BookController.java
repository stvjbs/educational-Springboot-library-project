package com.github.library.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.library.controllers.dto.BookDTO;
import com.github.library.entity.Book;
import com.github.library.services.BookService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("book")
@AllArgsConstructor
public class BookController {
    BookService bookService;

    @GetMapping("all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id));
    }

    @PostMapping("add")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }
}
