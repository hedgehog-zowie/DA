package com.iuni.data.avro.exceptions;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class RpcClientException extends RpcException {

    public RpcClientException(Throwable cause) {
        super(cause);
    }

    public RpcClientException(String message) {
        super(message);
    }

    public RpcClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
