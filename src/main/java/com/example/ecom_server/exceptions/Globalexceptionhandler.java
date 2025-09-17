package com.example.ecom_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class Globalexceptionhandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleNotFound(String msg){
		ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
	}

}
