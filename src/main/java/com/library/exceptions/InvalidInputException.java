package com.library.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.library.controllers.BookController;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {


	/**
	 * 
	 */
	private static final Logger logger=LoggerFactory.getLogger(BookController.class);
	private static final long serialVersionUID = -7596434247442941045L;

	private String resourceName;
	private String fieldName;
	private Object fieldValue;
	public InvalidInputException(String resourceName, String fieldName, Object fieldValue) {
		super();
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		
		logger.warn("Book with ID: {} is already {}",fieldValue,resourceName);
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}


	
}
