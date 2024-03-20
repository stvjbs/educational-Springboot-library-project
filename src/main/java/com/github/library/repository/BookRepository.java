package com.github.library.repository;

import org.springframework.stereotype.Repository;
import com.github.library.entity.Book;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<Book> list = new ArrayList<>();

    public Book findById(long id) {
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Book> findAllBooks() {
        return list;
    }

    public void addBook(Book book) {
        list.add(book);
    }

    public Book deleteBook(long bookId) {
        list.removeIf(x -> x.getId() == bookId);
        return findById(bookId);
    }
}
