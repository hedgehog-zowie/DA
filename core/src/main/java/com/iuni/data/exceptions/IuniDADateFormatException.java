package com.iuni.data.exceptions;

public class IuniDADateFormatException extends IuniDAException {

    private static final long serialVersionUID = 1L;

	public IuniDADateFormatException(Throwable cause) {
		super(cause);
	}

	public IuniDADateFormatException(String message) {
		super(message);
	}

	public IuniDADateFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
