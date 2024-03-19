package ru.gb.springbootlesson3.controllers.dto;

import lombok.Data;
@Data
public class IssueDTO {
    private long readerId;
    private long bookId;
}
