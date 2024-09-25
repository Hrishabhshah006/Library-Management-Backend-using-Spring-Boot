package com.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	public void sendBorrowNotification(String bookTitle,int bookId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("daditi870@gmail.com");
		message.setSubject("Book Borrowed Notification");
		message.setText("The book with title '"+ bookTitle +"' and ID " + bookId + " has been borrowed.");
		mailSender.send(message);
	}
}