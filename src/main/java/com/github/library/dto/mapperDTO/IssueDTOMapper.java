package com.github.library.dto.mapperDTO;

import com.github.library.dto.IssueDTO;
import com.github.library.entity.Issue;
import org.springframework.stereotype.Component;

@Component
public class IssueDTOMapper {
    public Issue mapToIssue(IssueDTO issueDTO) {
        return Issue.builder()
                .reader(issueDTO.getReader())
                .book(issueDTO.getBook())
                .build();
    }

    public IssueDTO mapToIssueDTO(Issue issue) {
        return IssueDTO.builder()
                .id(issue.getId())
                .book(issue.getBook())
                .reader(issue.getReader())
                .issuedAt(issue.getIssuedAt())
                .returnedAt(issue.getReturnedAt())
                .build();
    }
}
