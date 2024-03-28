package com.github.library.controllers;

import com.github.library.dto.BookDTO;
import com.github.library.entity.Book;
import com.github.library.exceptions.EntityValidationException;
import com.github.library.services.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
@AllArgsConstructor
@Slf4j
public class BookController {
    BookService bookService;

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @PostMapping()
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDTO bookDTO, Errors errors) {
        if (errors.hasErrors()) throw new EntityValidationException();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id));
    }

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<String> nameValidationExceptionHandler() {
        log.info("Name validation error.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Name must exists at least 3 characters.");
    }
}
