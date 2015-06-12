package com.iuni.data.exceptions;

public class IuniDAException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IuniDAException(String message) {
		super(message);
	}

	public IuniDAException(Throwable cause) {
		super(cause);
	}

	public IuniDAException(String message, Throwable cause) {
		super(message, cause);
	}
}
