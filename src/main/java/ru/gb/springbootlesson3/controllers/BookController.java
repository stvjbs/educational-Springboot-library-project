package ru.gb.springbootlesson3.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.controllers.dto.BookDTO;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.services.BookService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("book")
@AllArgsConstructor
public class BookController {
    BookService bookService;

    @GetMapping("allBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }
    @GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id));
    }

    @PostMapping("addBook")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }
}
