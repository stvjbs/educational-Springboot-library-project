package com.github.library.controllers;

import com.github.library.dto.BookDTO;
import com.github.library.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @PostMapping()
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
