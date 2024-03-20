package com.github.library.services;

import com.github.library.entity.Issue;
import com.github.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.library.controllers.dto.ReaderDTO;
import com.github.library.entity.Reader;
import com.github.library.repository.IssueRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReaderService {
    ReaderRepository readerRepository;
    IssueRepository issueRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAllReaders();
    }

    public Reader getReaderById(long id) {
        return readerRepository.findById(id);
    }

    public Reader addReader(ReaderDTO readerDTO) {
        Reader reader = new Reader(readerDTO.getName());
        return readerRepository.addReader(reader);
    }

    public List<Issue> getReadersIssues(long id) {
        return issueRepository.getMapIssues().get(id);
    }

    public Reader deleteReader(long readerId) {
        return readerRepository.deleteReader(readerId);
    }
}
