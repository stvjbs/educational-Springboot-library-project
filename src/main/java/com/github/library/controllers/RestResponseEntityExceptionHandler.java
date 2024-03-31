package com.github.library.controllers;

import com.github.library.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
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
