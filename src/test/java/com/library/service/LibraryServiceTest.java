package com.library.service;

import com.library.exceptions.InvalidInputException;
import com.library.exceptions.ResourceNotFound;
import com.library.models.Book;
import com.library.repositories.BookRepository;
import com.library.services.impl.BookServiceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceimplTest {

    @InjectMocks
    private BookServiceimpl bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1, "Test Book", "Ram", "00000", false);
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> books = bookService.getAllBooks();

        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1);

        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void getBookById_NotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> bookService.getBookById(1));
    }

    @Test
    void addNewBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.addNewBook(book);

        assertEquals(book, result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void borrowBook_Success() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.borrowBook(1);

        assertTrue(result.isBorrowed());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void borrowBook_AlreadyBorrowed() {
        book.setBorrowed(true);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        assertThrows(InvalidInputException.class, () -> bookService.borrowBook(1));
    }

    @Test
    void returnBook_Success() {
        book.setBorrowed(true);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.returnBook(1);

        assertFalse(result.isBorrowed());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void returnBook_NotBorrowed() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        assertThrows(InvalidInputException.class, () -> bookService.returnBook(1));
    }

    @Test
    void deleteBook_Success() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        boolean result = bookService.deleteBook(1);

        assertTrue(result);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> bookService.deleteBook(1));
    }
}
