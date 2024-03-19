package ru.gb.springbootlesson3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.repository.ReaderRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReaderService {
    ReaderRepository readerRepository;
    public List<Reader> getAllReaders() {
        return readerRepository.findAllReaders();
    }
    public Reader getReaderById(long id){
        return readerRepository.findById(id);
    }
    public Reader addReader(Reader reader) {
        return readerRepository.addReader(reader);
    }

    public Reader deleteReader(long readerId) {
        return readerRepository.deleteReader(readerId);
    }
}
