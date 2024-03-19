package ru.gb.springbootlesson3.controllers.Requests;

import lombok.Data;
@Data
public class IssueRequest {
    private long readerId;
    private long bookId;
}
