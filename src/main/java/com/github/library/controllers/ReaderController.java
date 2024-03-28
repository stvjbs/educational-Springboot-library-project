package com.github.library.controllers;

import com.github.library.dto.ReaderDTO;
import com.github.library.entity.Reader;
import com.github.library.exceptions.EntityValidationException;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.services.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("reader")
@RequiredArgsConstructor
@Slf4j
public class ReaderController {
    private final ReaderService readerService;

    @GetMapping()
    public ResponseEntity<List<Reader>> getAllReaders() {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getAllReaders());
    }

    @GetMapping("{id}")
    public ResponseEntity<Reader> getReader(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.getReaderById(id));
        } catch (NoSuchElementException e) {
            throw new NotFoundEntityException();
        }
    }

    @PostMapping()
    public ResponseEntity<Reader> addReader(@RequestBody @Valid ReaderDTO reader, Errors errors) {
        if (errors.hasErrors()) throw new EntityValidationException();
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.addReader(reader));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.deleteReader(id));
    }

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<String> nameValidationExceptionHandler() {
        log.info("Name validation error.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Name must exists at least 3 characters.");
    }
}

