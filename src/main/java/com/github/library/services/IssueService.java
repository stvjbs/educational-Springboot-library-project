package com.github.library.services;

import com.github.library.dto.IssueDTO;
import com.github.library.dto.mapperDTO.IssueDTOMapper;
import com.github.library.entity.Book;
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
import java.util.ArrayList;
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
    private final IssueDTOMapper issueDTOMapper;
    @Value("${spring.application.services.IssueService.maxAllowedBooks}")
    private int maxAllowedBooks;

    public IssueDTO createIssue(IssueDTO issueDTO) {
        Optional<Book> thisBook = bookRepository.findById(issueDTO.getBookDTO().getId());
        Optional<Reader> thisReader = readerRepository.findById(issueDTO.getReaderDTO().getId());
        if (thisBook.isEmpty() || thisReader.isEmpty()) throw new NotFoundEntityException();

        int countActiveIssues = issueRepository.countIssuesByReaderAndReturnedAtIsNull(thisReader.get());
        boolean isAllow = countActiveIssues < maxAllowedBooks;

        if (isAllow) {
            Issue currentIssue = issueDTOMapper.mapToIssue(issueDTO);
            issueRepository.save(currentIssue);
            return issueDTOMapper.mapToIssueDTO(currentIssue);
        } else throw new NotAllowException();
    }

    public IssueDTO findIssueById(long id) {
        return issueDTOMapper
                .mapToIssueDTO(issueRepository.findById(id).orElseThrow(NotFoundEntityException::new));
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
                .orElseThrow(NotFoundEntityException::new);
        if (thisIssue.getReturnedAt() == null) {
            thisIssue.setReturnedAt(LocalDateTime.now());
            issueRepository.flush();
            return issueDTOMapper.mapToIssueDTO(thisIssue);
        } else throw new AlreadyReturnedException();
    }

    public List<IssueDTO> getAllIssues() {
        List<IssueDTO> list = new ArrayList<>();
        issueRepository.findAll().forEach(x -> list.add(issueDTOMapper.mapToIssueDTO(x)));
        return list;
    }

}