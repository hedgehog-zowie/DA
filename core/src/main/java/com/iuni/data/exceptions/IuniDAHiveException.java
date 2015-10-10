package com.iuni.data.exceptions;

public class IuniDAHiveException extends IuniDAException {

    private static final long serialVersionUID = 1L;

	public IuniDAHiveException(Throwable cause) {
		super(cause);
	}

	public IuniDAHiveException(String message) {
		super(message);
	}

	public IuniDAHiveException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
