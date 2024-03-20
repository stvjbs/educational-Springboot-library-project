package com.github.library.repository;

import com.github.library.entity.Issue;
import com.github.library.exceptions.NotFoundIssueException;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Getter
public class IssueRepository {
    private final Map<Long, List<Issue>> mapIssues = new HashMap<>();

    public void createIssue(Issue issue) {
        if (this.mapIssues.get(issue.getIdReader()) != null) {
            List<Issue> issues = this.mapIssues.get(issue.getIdReader());
            issues.add(issue);
            mapIssues.put(issue.getIdReader(), issues);
        } else {
            List<Issue> issues = new ArrayList<>();
            issues.add(issue);
            mapIssues.put(issue.getIdReader(), issues);
        }
    }
    public List<Issue> getAllIssues(){
        List<Issue> allIssues = new ArrayList<>();
        mapIssues.values().forEach(allIssues::addAll);
        return allIssues;
    }

    public List<Issue> findByReaderId(long readerId) {
        return mapIssues.get(readerId);
    }

    public Optional<Issue> findIssueById(long issueId) {
        Collection<List<Issue>> allIssues = mapIssues.values();
        return allIssues.iterator().next()
                .stream().filter(x -> x.getId() == issueId).findFirst();
    }

    public Issue returnIssue(long issueId) {
        if (findIssueById(issueId).isPresent()) {
            findIssueById(issueId).get().setReturnedAt(LocalDateTime.now());
            return findIssueById(issueId).get();
        } else throw new NotFoundIssueException();
    }
}
