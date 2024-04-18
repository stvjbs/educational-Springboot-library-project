package com.github.library.services;

import com.github.library.config.aspect.counter.issueCounter.CounterIssue;
import com.github.library.config.aspect.counter.issueRefuseCounter.CounterRefuseIssue;
import com.github.library.config.aspect.timer.TimerCustomAnnotation;
import com.github.library.dto.IssueDTO;
import com.github.library.dto.mapperDTO.IssueDTOMapper;
import com.github.library.entity.Book;
import com.github.library.entity.Issue;
import com.github.library.entity.Reader;
import com.github.library.exceptions.AlreadyReturnedException;
import com.github.library.exceptions.NotAllowException;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.exceptions.NotFoundIssueException;
import com.github.library.repository.BookRepository;
import com.github.library.repository.IssueRepository;
import com.github.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties
@RequiredArgsConstructor
@Slf4j
@TimerCustomAnnotation
public class IssueService {
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final IssueDTOMapper issueDTOMapper;
    @Value("${spring.application.services.IssueService.maxAllowedBooks}")
    private int maxAllowedBooks;

    @CounterIssue
    @CounterRefuseIssue
    @TimerCustomAnnotation
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Book thisBook = bookRepository.findById(issueDTO.getBookDTO().getId())
                .orElseThrow(NotFoundEntityException::new);
        Reader thisReader = readerRepository.findById(issueDTO.getReaderDTO().getId())
                .orElseThrow(NotFoundEntityException::new);

        int countActiveIssues = issueRepository.countIssuesByReaderAndReturnedAtIsNull(thisReader);
        boolean isAllow = countActiveIssues < maxAllowedBooks;

        if (isAllow) {
            Issue currentIssue = Issue.builder()
                    .book(thisBook)
                    .reader(thisReader)
                    .issuedAt(LocalDateTime.now())
                    .returnedAt(null)
                    .build();
            issueRepository.save(currentIssue);
            return issueDTOMapper.mapToIssueDTO(currentIssue);
        } else throw new NotAllowException();
    }

    public IssueDTO findIssueById(long id) {
        return issueDTOMapper
                .mapToIssueDTO(issueRepository.findById(id).orElseThrow(NotFoundIssueException::new));
    }

    public List<IssueDTO> findIssueByReaderId(long idReader) {
        List<IssueDTO> list = new ArrayList<>();
        issueRepository.findAllByReader(
                        readerRepository.findById(idReader).orElseThrow(NotFoundEntityException::new))
                .forEach(x -> list.add(issueDTOMapper.mapToIssueDTO(x)));
        return list;
    }

    public IssueDTO returnIssue(long issueId) {
        Issue thisIssue = issueRepository.findById(issueId)
                .orElseThrow(NotFoundIssueException::new);
        if (thisIssue.getReturnedAt() == null) {
            thisIssue.setReturnedAt(LocalDateTime.now());
            issueRepository.flush();
            return issueDTOMapper.mapToIssueDTO(thisIssue);
        } else throw new AlreadyReturnedException();
    }

    public List<IssueDTO> getAllIssues() {
        return issueDTOMapper.mapToListDTO(issueRepository.findAll());
    }

}