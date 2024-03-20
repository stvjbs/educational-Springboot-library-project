package ru.gb.springbootlesson3.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.Exceptions.NotFoundIssueException;
import ru.gb.springbootlesson3.entity.Issue;

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
            findIssueById(issueId).get().setReturned_at(LocalDateTime.now());
            return findIssueById(issueId).get();
        } else throw new NotFoundIssueException();
    }
}
