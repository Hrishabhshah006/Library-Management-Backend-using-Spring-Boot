package com.library.services;

import java.util.List;

import com.library.models.Book;

public interface BookService {
//	Fetch books 
//	FetchBook by ID 
//	Add a new book 
//	Borrow a book 
//	Return a book 
//	Delete a book
	
	List<Book> getAllBooks();
	Book getBookById(int id);
	Book addNewBook(Book book);
	Book borrowBook(int id);
	Book returnBook(int id);
	boolean deleteBook(int id);
	
}
