package ru.gb.springbootlesson3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.controllers.dto.ReaderDTO;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.repository.IssueRepository;
import ru.gb.springbootlesson3.repository.ReaderRepository;

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
