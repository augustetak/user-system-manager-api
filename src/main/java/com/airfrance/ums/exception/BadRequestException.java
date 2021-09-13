package com.airfrance.ums.exception;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super("Bad request");
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause){
		super(message, cause);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}
}
