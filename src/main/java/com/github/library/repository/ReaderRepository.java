package com.github.library.repository;

import com.github.library.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> deleteReaderById(long id);
}
