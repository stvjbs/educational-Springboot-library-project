package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.Exceptions.NotAllowException;
import ru.gb.springbootlesson3.controllers.dto.IssueDTO;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.repository.BookRepository;
import ru.gb.springbootlesson3.repository.IssueRepository;
import ru.gb.springbootlesson3.repository.ReaderRepository;

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
                    .filter(x -> x.getReturned_at() == null).toList();
            if (activeIssueList.size() == maxAllowedBooks) {
                reader.setAllowIssue(false);
            }
        }
    }
}
