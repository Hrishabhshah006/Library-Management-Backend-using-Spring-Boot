package com.library.controller;

import com.library.controllers.BookController;
import com.library.models.Book;
import com.library.services.BookService;
import com.library.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private EmailService emailService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1, "Test Book", "Ram", "00000", false);
    }

    @Test
    void getAllBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(1, result.size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById() {
        when(bookService.getBookById(1)).thenReturn(book);

        Book result = bookController.getBookById(1);

        assertEquals(book, result);
        verify(bookService, times(1)).getBookById(1);
    }

    @Test
    void saveBook() {
        when(bookService.addNewBook(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.saveBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).addNewBook(any(Book.class));
    }

    @Test
    void borrowBook() {
        when(bookService.borrowBook(anyInt())).thenReturn(book);
        doNothing().when(emailService).sendBorrowNotification(anyString(), anyInt());

        Book result = bookController.borrowBook(1);

        assertEquals(book, result);
        verify(bookService, times(1)).borrowBook(1);
        verify(emailService, times(1)).sendBorrowNotification(book.getTitle(), book.getId());
    }

    @Test
    void returnBook() {
        when(bookService.returnBook(anyInt())).thenReturn(book);

        Book result = bookController.returnBook(1);

        assertEquals(book, result);
        verify(bookService, times(1)).returnBook(1);
    }

    @Test
    void deleteBook() {
        when(bookService.deleteBook(anyInt())).thenReturn(true);

        ResponseEntity<String> response = bookController.deleteTodo(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted Book", response.getBody());
        verify(bookService, times(1)).deleteBook(1);
    }
}
