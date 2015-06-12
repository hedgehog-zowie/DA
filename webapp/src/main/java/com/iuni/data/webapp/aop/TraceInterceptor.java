package com.iuni.data.webapp.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TraceInterceptor extends CustomizableTraceInterceptor {

	protected static Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);

	@Override
	protected void writeToLog(Log logger, String message, Throwable ex) {
		if (ex != null) {
			TraceInterceptor.logger.debug(message, ex);
		} else {
			TraceInterceptor.logger.debug(message);
		}
	}

	@Override
	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return true;
	}
}