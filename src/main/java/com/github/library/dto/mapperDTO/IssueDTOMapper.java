package com.github.library.dto.mapperDTO;

import com.github.library.dto.IssueDTO;
import com.github.library.entity.Issue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IssueDTOMapper {
    private final BookDTOMapper bookDTOMapper;
    private final ReaderDTOMapper readerDTOMapper;

//    public Issue mapToIssue(IssueDTO issueDTO) {
//        return Issue.builder()
//                .book(bookDTOMapper.mapToBook(issueDTO.getBookDTO()))
//                .reader(readerDTOMapper.mapToReader(issueDTO.getReaderDTO()))
//                .issuedAt(LocalDateTime.now())
//                .returnedAt(null)
//                .build();
//    }

    public IssueDTO mapToIssueDTO(Issue issue) {
        return IssueDTO.builder()
                .id(issue.getId())
                .bookDTO(bookDTOMapper.mapToBookDTO(issue.getBook()))
                .readerDTO(readerDTOMapper.mapToReaderDTO(issue.getReader()))
                .issuedAt(issue.getIssuedAt())
                .returnedAt(issue.getReturnedAt())
                .build();
    }

    public List<IssueDTO> mapToListDTO(List<Issue> list) {
        return list.stream().map(this::mapToIssueDTO).toList();
    }
}
