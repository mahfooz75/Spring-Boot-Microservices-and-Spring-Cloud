package com.selftech.microservices.userservice.security;

public class SecurityException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public SecurityException(String msg) {
		super(msg);
	}
	
	public SecurityException(Throwable error) {
		super(error);
	}
	
	public SecurityException(String msg, Throwable error) {
		super(msg, error);
	}
}
