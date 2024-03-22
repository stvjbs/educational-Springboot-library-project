package com.github.library.services;

import com.github.library.controllers.dto.ReaderDTO;
import com.github.library.entity.Reader;
import com.github.library.repository.IssueRepository;
import com.github.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReaderService {
    ReaderRepository readerRepository;
    IssueRepository issueRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(long id) {
        return readerRepository.findById(id).get();
    }

    public Reader addReader(ReaderDTO readerDTO) {
        Reader reader = new Reader(readerDTO.getName());
        return readerRepository.save(reader);
    }

    public Reader deleteReader(long readerId) {
        Reader reader = readerRepository.findById(readerId).get();
        readerRepository.delete(reader);
        return reader;
    }
}
