package com.github.library.services;

import com.github.library.controllers.dto.IssueDTO;
import com.github.library.entity.Issue;
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

@Slf4j
@RequiredArgsConstructor
@Service
@EnableConfigurationProperties
public class IssueService {
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    @Value("${spring.application.services.IssueService.maxAllowedBooks}")
    public int maxAllowedBooks;

    public Issue createIssue(IssueDTO request) throws RuntimeException {
        if (bookRepository.findById(request.getBookId()).isEmpty() ||
                readerRepository.findById(request.getReaderId()).isEmpty()) throw new NotFoundEntityException();
        boolean isAllow = readerRepository.findById(request.getReaderId()).get().isAllowIssue();
        if (isAllow) {
            Issue currentIssue = new Issue(request.getReaderId(), request.getBookId());
            issueRepository.save(currentIssue);
            readerAllowSetter(request.getReaderId());
            return currentIssue;
        } else throw new NotAllowException();
    }

    public Optional<Issue> findIssueById(long id) {
        return issueRepository.findById(id);
    }

    public List<Issue> findIssueByReaderId(long idReader) {
        return issueRepository.findAllByIdReader(idReader);
    }

    public Issue returnIssue(long issueId) {
        if (findIssueById(issueId).get().getReturnedAt() == null) {
            long readerId = findIssueById(issueId).get().getIdReader();
            findIssueById(issueId).get().setReturnedAt(LocalDateTime.now());
            issueRepository.flush();
            readerAllowSetter(readerId);
            return issueRepository.findById(issueId).get();
        } else throw new AlreadyReturnedException();
    }


    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    private int countOfActiveIssues(Long readerId) {
        return issueRepository.findAllByIdReader(readerId)
                .stream().filter(x -> x.getReturnedAt() == null).toList().size();
    }

    private void readerAllowSetter(Long readerId) {
        if (countOfActiveIssues(readerId) >= maxAllowedBooks) {
            readerRepository.findById(readerId).get().setAllowIssue(false);
            readerRepository.flush();
        } else {
            readerRepository.findById(readerId).get().setAllowIssue(true);
            readerRepository.flush();
        }
    }
}