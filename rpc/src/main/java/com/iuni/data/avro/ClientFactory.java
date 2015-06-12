package com.iuni.data.avro;

import com.iuni.data.avro.client.Client;
import com.iuni.data.avro.exceptions.RpcClientException;
import org.apache.avro.Protocol;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ClientFactory {
    Client create(String name, Protocol protocol, String host, Integer port, String type) throws RpcClientException;

    Client create(String name, String type) throws RpcClientException;

    Class<? extends Client> getClass(String type) throws RpcClientException;
}
