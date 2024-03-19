package ru.gb.springbootlesson3.entity;

import lombok.Data;

@Data
public class Reader {
    private static long genId;
    private final long id;
    private final String name;
    private boolean allowIssue;
    public Reader(String name) {
        id = genId++;
        this.name = name;
        allowIssue = true;
    }
}
