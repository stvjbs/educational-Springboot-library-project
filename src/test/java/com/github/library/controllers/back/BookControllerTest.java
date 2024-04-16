package com.github.library.controllers.back;

import com.github.library.dto.BookDTO;
import com.github.library.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;


    @Autowired
    private BookController bookController;

    @Test
    void getAllBooks() {
        List<BookDTO> bookDTOList = List.of(new BookDTO("TestBook1"),
                new BookDTO("TestBook2"));
        when(bookService.getAllBooks()).thenReturn(bookDTOList);

        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookDTOList.size(), response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBook() {
        BookDTO bookDTO = new BookDTO("TestBook1");
        bookDTO.setId(1);
        when(bookService.getBookById(1)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.getBook(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookDTO, response.getBody());
        verify(bookService, times(1)).getBookById(1);
    }

    @Test
    void addBook() {
        BookDTO bookDTO = new BookDTO("TestBook1");
        bookDTO.setId(1);
        when(bookService.addBook(bookDTO)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.addBook(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookDTO, response.getBody());
        verify(bookService, times(1)).addBook(bookDTO);
    }

    @Test
    void deleteBook() {
        long bookId = 1L;

        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(bookId);
    }
}