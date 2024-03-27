package com.github.library.services;

import com.github.library.dto.IssueDTO;
import com.github.library.entity.Issue;
import com.github.library.entity.Reader;
import com.github.library.exceptions.AlreadyReturnedException;
import com.github.library.exceptions.NotAllowException;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.repository.BookRepository;
import com.github.library.repository.IssueRepository;
import com.github.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@EnableConfigurationProperties
@RequiredArgsConstructor
@Slf4j
public class IssueService {
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    @Value("${spring.application.services.IssueService.maxAllowedBooks}")
    private int maxAllowedBooks;

    public Issue createIssue(IssueDTO request) throws RuntimeException {
        if (bookRepository.findById(request.getBookId()).isEmpty() ||
                readerRepository.findById(request.getReaderId()).isEmpty()) throw new NotFoundEntityException();
        Reader thisReader = readerRepository.findById(request.getReaderId()).get();
        int countActiveIssues = issueRepository.countIssuesByReaderAndReturnedAtIsNull(thisReader);
        boolean isAllow = countActiveIssues < maxAllowedBooks;
        if (isAllow) {
            Issue currentIssue = new Issue(readerRepository.findById(request.getReaderId()).get(),
                    bookRepository.findById(request.getBookId()).get());
            issueRepository.save(currentIssue);
            return currentIssue;
        } else throw new NotAllowException();
    }

    public Optional<Issue> findIssueById(long id) {
        return issueRepository.findById(id);
    }

    public List<Issue> findIssueByReaderId(long idReader) {
        return issueRepository.findAllByReader(readerRepository.findById(idReader).get());
    }

    public Issue returnIssue(long issueId) {
        if (findIssueById(issueId).get().getReturnedAt() == null) {
            findIssueById(issueId).get().setReturnedAt(LocalDateTime.now());
            issueRepository.flush();
            return issueRepository.findById(issueId).get();
        } else throw new AlreadyReturnedException();
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

}