package ru.gb.springbootlesson3.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.controllers.dto.ReaderDTO;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.services.ReaderService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("reader")
@AllArgsConstructor
public class ReaderController {
    ReaderService readerService;

    @GetMapping("all")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getAllReaders());
    }

    @GetMapping("{id}")
    public ResponseEntity<Reader> getReader(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getReaderById(id));
    }

    @GetMapping("{id}/issue")
    public ResponseEntity<List<Issue>> getReadersIssues(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getReadersIssues(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.deleteReader(id));
    }

    @PostMapping("add")
    public ResponseEntity<Reader> addReader(@RequestBody ReaderDTO reader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.addReader(reader));
    }
}
