package com.github.library.repository;

import com.github.library.entity.Issue;
import com.github.library.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByReader(Reader reader);
    int countIssuesByReaderAndReturnedAtIsNull(Reader reader);
}
