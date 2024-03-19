package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Reader;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReaderRepository {
    private final List<Reader> list = new ArrayList<>();

    public Reader findById(long id) {
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Reader> findAllReaders() {
        return list;
    }

    public Reader addReader(Reader reader) {
        list.add(reader);
        return reader;
    }

    public Reader deleteReader(long readerId) {
        list.removeIf(x -> x.getId() == readerId);
        return findById(readerId);
    }
}
