package com.example.vanshawali3.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException() {
		super("ResourceNotFoundException on the server.");
	}
	
}
