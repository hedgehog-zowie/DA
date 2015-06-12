package com.iuni.data.avro.exceptions;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class RpcException extends Exception{

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
