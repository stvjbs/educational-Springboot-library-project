package com.github.library.services;

import com.github.library.controllers.dto.IssueDTO;
import com.github.library.entity.Issue;
import com.github.library.exceptions.AlreadyReturnedException;
import com.github.library.exceptions.NotAllowException;
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
    @Value("${spring.application.services.IssueService.maxAllowedBooks}")
    public int maxAllowedBooks;

    public Issue createIssue(IssueDTO request) throws NotAllowException {
        boolean isAllow = readerRepository.findById(request.getReaderId()).get().isAllowIssue();
        Issue currentIssue = new Issue(request.getReaderId(), request.getBookId());
        if (isAllow) {
            issueRepository.save(currentIssue);

            int countOfActiveIssues = issueRepository.findAllByIdReader(request.getReaderId())
                    .stream().filter(x -> x.getReturnedAt() == null).toList().size();

            if (countOfActiveIssues == maxAllowedBooks) {
                readerRepository.findById(request.getReaderId()).get().setAllowIssue(false);
            }
            return currentIssue;
        } else throw new NotAllowException();
    }

    public Optional<Issue> findIssueById(long id) {
        return issueRepository.findById(id);
    }
    public List<Issue> findIssueByReaderId(long idReader) {
        return issueRepository.findAllByIdReader(idReader);
    }

    public Issue returnIssue(long issueId)  {
        if (findIssueById(issueId).get().getReturnedAt() == null) {
            long readerId = findIssueById(issueId).get().getIdReader();
            readerRepository.findById(readerId).get().setAllowIssue(true);
            findIssueById(issueId).get().setReturnedAt(LocalDateTime.now());
            return issueRepository.findById(issueId).get();
        }
        else throw new AlreadyReturnedException();
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }
}