package com.github.library.controllers;

import com.github.library.dto.BookDTO;
import com.github.library.entity.Book;
import com.github.library.exceptions.NameValidationException;
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
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDTO bookDTO, Errors errors) {
        if (errors.hasErrors()) throw new NameValidationException();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @ExceptionHandler(NameValidationException.class)
    public ResponseEntity<String> nameValidationExceptionHandler() {
        log.info("Ошибка валидации имени.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Имя должно содержать минимум 3 символа.");
    }
}
