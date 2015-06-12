package com.iuni.data.avro;

import com.google.common.base.Preconditions;
import com.iuni.data.avro.client.Client;
import com.iuni.data.avro.client.ClientType;
import com.iuni.data.avro.exceptions.RpcClientException;
import org.apache.avro.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

//import org.apache.avro.generic.GenericRecord;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class DefaultClientFactory implements ClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultClientFactory.class);

    @Override
    public Client create(String name, String type) throws RpcClientException {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(type, "type");
        logger.info("Creating instance of client: {}, type: {}", name, type);
        Class<? extends Client> clientClass = getClass(type);
        Constructor<?> constructor;
        try {
            constructor = clientClass.getConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            String errorStr = new StringBuilder()
                    .append("constructor is not exist, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
        try {
            Client abstractClient = (Client) constructor.newInstance();
            abstractClient.setName(name);
            return abstractClient;
        } catch (Exception e) {
            String errorStr = new StringBuilder()
                    .append("Create client failed, type: ")
                    .append(type)
                    .append(", class: ")
                    .append(clientClass.getName())
                    .append(". error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
    }

    @Override
    public Client create(String name, Protocol protocol, String host, Integer port, String type) throws RpcClientException {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(protocol, "protocol");
        Preconditions.checkNotNull(host, "host");
        Preconditions.checkNotNull(port, "port");
        Preconditions.checkNotNull(type, "type");
        logger.info("Creating instance of client: {}, type: {}", name, type);
        Class<? extends Client> clientClass = getClass(type);
        Constructor<?> constructor;
        try {
            constructor = clientClass.getConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            String errorStr = new StringBuilder()
                    .append("constructor is not exist, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
        try {
            Client abstractClient = (Client) constructor.newInstance();
            abstractClient.setName(name);
            return abstractClient;
        } catch (Exception e) {
            String errorStr = new StringBuilder()
                    .append("Create client failed, type: ")
                    .append(type)
                    .append(", class: ")
                    .append(clientClass.getName())
                    .append(". error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
    }

    @Override
    public Class<? extends Client> getClass(String type) throws RpcClientException {
        String clientClassName = type;
        ClientType clientType = ClientType.OTHER;
        try {
            clientType = ClientType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            logger.debug("Client type {} is a custom type", type);
        }
        if (!clientType.equals(ClientType.OTHER)) {
            clientClassName = clientType.getClientClassName();
        }
        try {
            return (Class<? extends Client>) Class.forName(clientClassName);
        } catch (Exception e) {
            String errorStr = new StringBuilder()
                    .append("Unable to load client type:")
                    .append(type)
                    .append(", class: ")
                    .append(clientClassName)
                    .append("error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
    }

}
