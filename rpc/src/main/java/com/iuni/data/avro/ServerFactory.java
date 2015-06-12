package com.iuni.data.avro;

import com.iuni.data.avro.exceptions.RpcServerException;
import com.iuni.data.avro.server.Server;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ServerFactory {
    Server create(String name, String type) throws RpcServerException;
    Class<? extends Server> getClass(String type) throws RpcServerException;
}
