package com.library.services.impl;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.exceptions.InvalidInputException;
import com.library.exceptions.ResourceNotFound;
import com.library.models.Book;
import com.library.repositories.BookRepository;
import com.library.services.BookService;

@Service
public class BookServiceimpl implements BookService {

	@Autowired
	private BookRepository books;
	
	
	@Override
	public List<Book> getAllBooks() {
		return books.findAll();
	}

	@Override
	public Book getBookById(int id) {
//		Optional<Book> book = books.findById(id); 
//		if(book.isPresent())
//			return book.get();
		return books.findById(id).orElseThrow (() -> new ResourceNotFound("books", "id", id));
	}

	@Override
	public Book addNewBook(Book book) {
		
		return books.save(book);
	}

	@Override
	public Book borrowBook(int id) {
		Book book = books.findById(id).orElseThrow (() -> new ResourceNotFound("books", "id", id));;
		if(book.isBorrowed()==false) {
			book.setBorrowed(true);
			books.save(book);
		}
		else {
			throw new InvalidInputException("Borrowed", "id", id);
		}
		return book;
		
	}

	@Override
	public Book returnBook(int id) {
		Book book = books.findById(id).orElseThrow (() -> new ResourceNotFound("books", "id", id));
		if(book.isBorrowed()==true) {
			book.setBorrowed(false);
			books.save(book);
		}
		else {
			throw new InvalidInputException("Returned", "id", id);
		}
		return book;


	}

	@Override
	public boolean deleteBook(int id) {
		Book book = books.findById(id).orElseThrow (() -> new ResourceNotFound("books", "id", id));
		books.delete(book);
		return true;
	}

}
