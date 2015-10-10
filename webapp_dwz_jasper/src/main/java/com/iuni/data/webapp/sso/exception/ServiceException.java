package com.iuni.data.webapp.sso.exception;

/**
 * 公共服务异常类 
 * @ClassName: ServiceException 
 * @Description: 公共服务异常类 
 * @author ZuoChangjun
 * @date 2013-07-10 上午16:41:55 
 * @version dp-admin-1.0.0
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 5153180652772541214L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
