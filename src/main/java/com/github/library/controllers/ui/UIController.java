package com.github.library.controllers.ui;

import com.github.library.dto.BookDTO;
import com.github.library.services.BookService;
import com.github.library.services.IssueService;
import com.github.library.services.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ui")
@RequiredArgsConstructor
@EnableWebSecurity
public class UIController {
    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueService issueService;

    @GetMapping("books")
    public String booksDisplay(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("readers")
    public String readersDisplay(Model model) {
        model.addAttribute("readers", readerService.getAllReaders());
        return "readers";
    }

    @GetMapping("issues")
    public String issuesTable(Model model) {
        model.addAttribute("issues", issueService.getAllIssues());
        return "issues";
    }

    @GetMapping("reader/{id}")
    public String issuesOfReader(@PathVariable long id, Model model) {
        model.addAttribute("name", readerService.getReaderById(id).getName());
        model.addAttribute("id", id);
        List<BookDTO> books = new ArrayList<>();
        issueService.findIssueByReaderId(id)
                .forEach(x -> books.add(bookService.getBookById(x.getBookDTO().getId())));
        model.addAttribute("books", books);
        return "reader";
    }
}