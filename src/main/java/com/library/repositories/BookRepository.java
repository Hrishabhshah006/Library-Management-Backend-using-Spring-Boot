package com.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	

}
