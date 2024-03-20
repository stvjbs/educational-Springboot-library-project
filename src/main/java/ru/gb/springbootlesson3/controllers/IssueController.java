package ru.gb.springbootlesson3.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.exceptions.NotAllowException;
import ru.gb.springbootlesson3.exceptions.NotFoundEntityException;
import ru.gb.springbootlesson3.exceptions.NotFoundIssueException;
import ru.gb.springbootlesson3.controllers.dto.IssueDTO;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.services.IssueService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("issue")
@RequiredArgsConstructor
public class IssueController {
    private IssueService service;

    @Autowired
    public IssueController(IssueService service) {
        this.service = service;
    }

    @PostMapping("add")
    public ResponseEntity<Issue> issueBook(@RequestBody IssueDTO issueDTO) {
        log.info("Поступил запрос на выдачу: readerId={}, bookId={}"
                , issueDTO.getReaderId(), issueDTO.getBookId());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createIssue(issueDTO));
        } catch (NoSuchElementException e) {
            throw new NotFoundEntityException();
        }
    }

    @GetMapping("{readerId}")
    public ResponseEntity<List<Issue>> getIssueByReaderId(@PathVariable long readerId) {
        log.info("Поступил запрос отображения выдач по id читателя: readerId={}"
                , readerId);
        if (service.getIssueByReaderId(readerId) == null) {
            throw new NotFoundEntityException();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getIssueByReaderId(readerId));
    }

    @GetMapping("/issues/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable long issueId) {
        log.info("Поступил запрос отображения выдачи по id: issueId={}"
                , issueId);
        if (service.findIssueById(issueId).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.findIssueById(issueId).get());
        } else throw new NotFoundIssueException();
    }

    @PutMapping("{issueId}")
    public ResponseEntity<Issue> returnIssue(@PathVariable long issueId) {
        log.info("Поступил запрос возврата выдачи: issueId={}"
                , issueId);
        return ResponseEntity.status(HttpStatus.OK).body(service.returnIssue(issueId));
    }

    @ExceptionHandler(NotFoundIssueException.class)
    public ResponseEntity<String> notFoundIssueExceptionHandler() {
        log.info("Такой выдачи не существует. Повторите попытку");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Такой выдачи не существует. Повторите попытку");
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<String> notFoundEntityExceptionHandler() {
        log.info("Такого читателя или книги не существует. Повторите попытку");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Такого читателя или книги не существует. Повторите попытку");
    }

    @ExceptionHandler(NotAllowException.class)
    public ResponseEntity<String> notAllowExceptionHandler() {
        log.info("У данного читателя достигнут лимит взятых книг.");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("У данного читателя достигнут лимит взятых книг.");
    }
}
