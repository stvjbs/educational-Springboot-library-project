package ru.gb.springbootlesson3.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Issue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Getter
public class IssueRepository {
    private final Map<Long, List<Issue>> mapIssues = new HashMap<>();

    public void createIssue(Issue issue) {
        if (this.mapIssues.get(issue.getIdReader()) != null) {
            List<Issue> issues = this.mapIssues.get(issue.getIdReader());
            issues.add(issue);
            mapIssues.put(issue.getIdReader(), issues);
        }
        else {
            List<Issue> issues = new ArrayList<>();
            issues.add(issue);
            mapIssues.put(issue.getIdReader(), issues);
        }
    }

    public List<Issue> findById(long id) {
        return mapIssues.get(id);
    }
}
