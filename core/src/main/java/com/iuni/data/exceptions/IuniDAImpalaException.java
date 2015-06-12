package com.iuni.data.exceptions;

public class IuniDAImpalaException extends IuniDAException {

    private static final long serialVersionUID = 1L;

	public IuniDAImpalaException(Throwable cause) {
		super(cause);
	}

	public IuniDAImpalaException(String message) {
		super(message);
	}

	public IuniDAImpalaException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
