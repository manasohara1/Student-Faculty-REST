package com.api.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FacultyExceptionController {
	@ExceptionHandler(value = FacultyInvalidException.class)

	public ResponseEntity<Object> exception(FacultyInvalidException exception) {
		return new ResponseEntity<>("Faculty Information Invalid", HttpStatus.NOT_FOUND);
	}
	

}
