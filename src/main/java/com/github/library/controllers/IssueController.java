package com.github.library.controllers;

import com.github.library.controllers.dto.IssueDTO;
import com.github.library.entity.Issue;
import com.github.library.exceptions.AlreadyReturnedException;
import com.github.library.exceptions.NotAllowException;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.exceptions.NotFoundIssueException;
import com.github.library.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("issue")
@RequiredArgsConstructor
public class IssueController {
    private IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("add")
    public ResponseEntity<Issue> issueBook(@RequestBody IssueDTO issueDTO) {
        log.info("Поступил запрос на выдачу: readerId={}, bookId={}"
                , issueDTO.getReaderId(), issueDTO.getBookId());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueDTO));
        } catch (NoSuchElementException e) {
            throw new NotFoundEntityException();
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }

    }

    @GetMapping("/issues/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable long issueId) {
        log.info("Поступил запрос отображения выдачи по id: issueId={}"
                , issueId);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(issueService.findIssueById(issueId).get());
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }
    }

    @PutMapping("{issueId}")
    public ResponseEntity<Issue> returnIssue(@PathVariable long issueId) {
        log.info("Поступил запрос возврата выдачи: issueId={}"
                , issueId);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.returnIssue(issueId));
        } catch (NoSuchElementException e) {
            throw new NotFoundIssueException();
        }
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

    @ExceptionHandler(AlreadyReturnedException.class)
    public ResponseEntity<String> alreadyReturnedExceptionHandler() {
        log.info("Выдача была возвращена ранее.");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Выдача была возвращена ранее.");
    }
}
