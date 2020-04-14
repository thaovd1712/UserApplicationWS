package com.thao.wsapplication.exception;

public class UserServiceException extends RuntimeException{
	private static final long serialVersionUID = 4533901698262281824L;

	public UserServiceException(String message) {
		super(message);
	}
	
}
