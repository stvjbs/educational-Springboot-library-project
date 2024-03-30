package com.github.library.controllers;

import com.github.library.dto.IssueDTO;
import com.github.library.entity.Issue;
import com.github.library.exceptions.*;
import com.github.library.services.IssueService;
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
@RequestMapping("issue")
@RequiredArgsConstructor
@Slf4j
public class IssueController {
    private final IssueService issueService;

    @GetMapping()
    public ResponseEntity<List<Issue>> findAllIssues() {
        log.info("Received a request to display all issues");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.getAllIssues());
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }
    }

    @GetMapping("{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable long issueId) {
        log.info("Received a request to display issue by id: issueId={}", issueId);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.findIssueById(issueId).get());
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }
    }

    @GetMapping("/reader")
    public ResponseEntity<List<Issue>> getIssuesByReaderId(@RequestParam long readerId) {
        log.info("Received a request to display issues by reader's id: readerId={}"
                , readerId);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(issueService.findIssueByReaderId(readerId));
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }
    }

    @PostMapping()
    public ResponseEntity<Issue> issueBook(@RequestBody @Valid IssueDTO issueDTO, Errors errors) {
        log.info("Received a request to issue: readerId={}, bookId={}", issueDTO.getReader(), issueDTO.getBook());
        if (errors.hasErrors()) throw new EntityValidationException();
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueDTO));
        } catch (NoSuchElementException e) {
            throw new NotFoundEntityException();
        } catch (NullPointerException e) {
            throw new NotFoundIssueException();
        }

    }

    @PutMapping("{issueId}")
    public ResponseEntity<Issue> returnIssue(@PathVariable long issueId) {
        log.info("Received a request to return the issue: issueId={}", issueId);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.returnIssue(issueId));
        } catch (NoSuchElementException e) {
            throw new NotFoundIssueException();
        }
    }

    @ExceptionHandler(NotFoundIssueException.class)
    public ResponseEntity<String> notFoundIssueExceptionHandler() {
        log.info("There is no such issue. Try again.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such issue. Try again.");
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<String> notFoundEntityExceptionHandler() {
        log.info("There is no such reader or book. Try again.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such reader or book. Try again.");
    }

    @ExceptionHandler(NotAllowException.class)
    public ResponseEntity<String> notAllowExceptionHandler() {
        log.info("This reader has reached the limit of borrowed books.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This reader has reached the limit of borrowed books.");
    }

    @ExceptionHandler(AlreadyReturnedException.class)
    public ResponseEntity<String> alreadyReturnedExceptionHandler() {
        log.info("The issue was returned earlier.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The issue was returned earlier.");
    }
    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<String> nameValidationExceptionHandler() {
        log.info("Entity validation error.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity is not valid.");
    }
}
