package com.project.LendingEngineAppKafka.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String username) {
		super("User Not Found :"+username);
		// TODO Auto-generated constructor stub
	}

	
	
}
