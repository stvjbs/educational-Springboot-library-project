package com.github.library.controllers;

import com.github.library.dto.ReaderDTO;
import com.github.library.entity.Issue;
import com.github.library.entity.Reader;
import com.github.library.exceptions.NameValidationException;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.exceptions.NotFoundIssueException;
import com.github.library.services.IssueService;
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
    private final IssueService issueService;

    @GetMapping("all")
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

    @GetMapping("{readerId}/issue")
    public ResponseEntity<List<Issue>> getIssuesByReaderId(@PathVariable long readerId) {
        log.info("Поступил запрос отображения выдач по id читателя: readerId={}"
                , readerId);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(issueService.findIssueByReaderId(readerId));
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.deleteReader(id));
    }

    @PostMapping("add")
    public ResponseEntity<Reader> addReader(@RequestBody @Valid ReaderDTO reader, Errors errors) {
        if (errors.hasErrors()) throw new NameValidationException();
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.addReader(reader));
    }

    @ExceptionHandler(NameValidationException.class)
    public ResponseEntity<String> nameValidationExceptionHandler() {
        log.info("Ошибка валидации имени.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Имя должно содержать минимум 3 символа.");
    }
}

