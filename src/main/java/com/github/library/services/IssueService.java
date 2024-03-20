package com.github.library.services;

import com.github.library.entity.Issue;
import com.github.library.repository.BookRepository;
import com.github.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import com.github.library.exceptions.NotAllowException;
import com.github.library.controllers.dto.IssueDTO;
import com.github.library.entity.Book;
import com.github.library.entity.Reader;
import com.github.library.repository.IssueRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableConfigurationProperties
public class IssueService {
    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;
    @Value("${maxAllowedBooks}")
    public int maxAllowedBooks;

    public Issue createIssue(IssueDTO request) throws NotAllowException {
        List<Issue> issueList = issueRepository.getMapIssues().get(request.getReaderId());
        Book thisBook = bookRepository.findById(request.getBookId());
        Reader thisReader = readerRepository.findById(request.getReaderId());
        Issue thisIssue = new Issue(request.getReaderId(), request.getBookId());
        entityNotNullChecker(thisBook, thisReader, request);
        readerAllowanceChecker(issueList, thisReader);
        if (thisReader.isAllowIssue()) {
            issueRepository.createIssue(thisIssue);
            return thisIssue;
        } else throw new NotAllowException();
    }

    public List<Issue> getIssueByReaderId(long id) {
        return issueRepository.findByReaderId(id);
    }

    public Optional<Issue> findIssueById(long id) {
        return issueRepository.findIssueById(id);
    }

    public Issue returnIssue(long issueId) {
        long readerId = findIssueById(issueId).get().getIdReader();
        readerRepository.findById(readerId).setAllowIssue(true);
        return issueRepository.returnIssue(issueId);
    }

    private void entityNotNullChecker(Book book, Reader reader, IssueDTO request) {
        if (book == null) {
            log.info("Не удалось найти книгу с id " + request.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу с id " + request.getBookId());
        }
        if (reader == null) {
            log.info("Не удалось найти читателя с id " + request.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя с id " + request.getReaderId());
        }
    }

    private void readerAllowanceChecker(List<Issue> issueList, Reader reader) {
        if (issueList != null) {
            List<Issue> activeIssueList = issueList.stream()
                    .filter(x -> x.getReturnedAt() == null).toList();
            if (activeIssueList.size() == maxAllowedBooks) {
                reader.setAllowIssue(false);
            }
        }
    }
}
