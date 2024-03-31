package com.github.library.controllers;

import com.github.library.dto.IssueDTO;
import com.github.library.exceptions.EntityValidationException;
import com.github.library.services.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
@Slf4j
public class IssueController {
    private final IssueService issueService;

    @GetMapping()
    public ResponseEntity<List<IssueDTO>> findAllIssues() {
        log.info("Received a request to display all issues");
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getAllIssues());
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable long issueId) {
        log.info("Received a request to display issue by id: issueId={}", issueId);
        return ResponseEntity.status(HttpStatus.OK).body(issueService.findIssueById(issueId));
    }

    @GetMapping("/reader")
    public ResponseEntity<List<IssueDTO>> getIssuesByReaderId(@RequestParam long readerId) {
        log.info("Received a request to display issues by reader's id: readerId={}"
                , readerId);
        return ResponseEntity.status(HttpStatus.OK).body(issueService.findIssueByReaderId(readerId));
    }

    @PostMapping()
    public ResponseEntity<IssueDTO> issueBook(@RequestBody @Valid IssueDTO issueDTO, Errors errors) {
        log.info("Received a request to issue: readerId={}, bookId={}", issueDTO.getReader(), issueDTO.getBook());
        if (errors.hasErrors()) throw new EntityValidationException();
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueDTO));
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<IssueDTO> returnIssue(@PathVariable long issueId) {
        log.info("Received a request to return the issue: issueId={}", issueId);
        return ResponseEntity.status(HttpStatus.OK).body(issueService.returnIssue(issueId));
    }
}
