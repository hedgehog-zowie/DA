package com.iuni.data.avro.exceptions;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class RpcServerException extends RpcException {

    public RpcServerException(Throwable cause) {
        super(cause);
    }

    public RpcServerException(String message) {
        super(message);
    }

    public RpcServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
