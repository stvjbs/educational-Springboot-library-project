package ru.gb.springbootlesson3.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Book {
    private static long genId;
    private final long id;
    private final String name;

    public Book(String name) {
        id = genId++;
        this.name = name;
    }
}
