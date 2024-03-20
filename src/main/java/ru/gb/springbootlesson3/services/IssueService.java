package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.Exceptions.NotAllowException;
import ru.gb.springbootlesson3.controllers.dto.IssueDTO;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.repository.BookRepository;
import ru.gb.springbootlesson3.repository.IssueRepository;
import ru.gb.springbootlesson3.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueService {
    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;

    public Issue createIssue(IssueDTO request) throws NotAllowException{
        if (bookRepository.findById(request.getBookId()) == null){
            log.info("Не удалось найти книгу с id " + request.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу с id " + request.getBookId());
        }
        if (readerRepository.findById(request.getReaderId()) == null){
            log.info("Не удалось найти читателя с id " + request.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя с id " + request.getReaderId());
        }
        if (readerRepository.findById(request.getReaderId()).isAllowIssue()) {
            readerRepository.findById(request.getReaderId()).setAllowIssue(false);
            Issue issue = new Issue(request.getReaderId(), request.getBookId());
            issueRepository.createIssue(issue);
            return issue;
        }
        else throw new NotAllowException();
    }
    public List<Issue> getIssueByReaderId(long id){
        return issueRepository.findByReaderId(id);
    }
    public Optional<Issue> findIssueById (long id){
        return issueRepository.findIssueById(id);
    }
    public Issue returnIssue(long issueId){
        long readerId = findIssueById(issueId).get().getIdReader();
        readerRepository.findById(readerId).setAllowIssue(true);
        return issueRepository.returnIssue(issueId);
    }

}
