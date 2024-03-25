package com.github.library.services;

import com.github.library.controllers.dto.ReaderDTO;
import com.github.library.entity.Reader;
import com.github.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(long id) {
        return readerRepository.findById(id).get();
    }

    public Reader addReader(ReaderDTO readerDTO) {
        Reader reader = new Reader(readerDTO.getName());
        return readerRepository.save(reader);
    }

    public Reader deleteReader(long readerId) {
        Reader reader = readerRepository.findById(readerId).get();
        readerRepository.delete(reader);
        return reader;
    }
}
