package com.github.library.controllers.back;

import com.github.library.config.aspect.timer.Timer;
import com.github.library.dto.ReaderDTO;
import com.github.library.exceptions.EntityValidationException;
import com.github.library.services.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reader")
@RequiredArgsConstructor
@Slf4j
@Timer
public class ReaderController {
    private final ReaderService readerService;

    @GetMapping()
    public ResponseEntity<List<ReaderDTO>> getAllReaders() {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getAllReaders());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReaderDTO> getReader(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getReaderById(id));
    }

    @PostMapping()
    public ResponseEntity<ReaderDTO> addReader(@RequestBody @Valid ReaderDTO readerDTO, Errors errors) {
        if (errors.hasErrors()) throw new EntityValidationException();
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.addReader(readerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable long id) {
        readerService.deleteReader(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}