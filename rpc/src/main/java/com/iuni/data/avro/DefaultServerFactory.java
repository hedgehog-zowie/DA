package com.iuni.data.avro;

import com.google.common.base.Preconditions;
import com.iuni.data.avro.exceptions.RpcServerException;
import com.iuni.data.avro.server.Server;
import com.iuni.data.avro.server.ServerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class DefaultServerFactory implements ServerFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServerFactory.class);

    @Override
    public Server create(String name, String type) throws RpcServerException {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(type, "type");
        logger.info("Creating instance of server: {}, type: {}", name, type);
        Class<? extends Server> serverClass = getClass(type);
        try {
            Server server = serverClass.newInstance();
            server.setName(name);
            return server;
        } catch (Exception e) {
            String errorStr = new StringBuilder()
                    .append("Create server failed, type: ")
                    .append(type)
                    .append(", class: ")
                    .append(serverClass.getName())
                    .append(". error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcServerException(errorStr);
        }
    }

    public Class<? extends Server> getClass(String type) throws RpcServerException {
        String serverClassName = type;
        ServerType serverType = ServerType.OTHER;
        try {
            serverType = ServerType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            logger.debug("Server type {} is a custom type", type);
        }
        if (!serverType.equals(ServerType.OTHER)) {
            serverClassName = serverType.getServerClassName();
        }
        try {
            return (Class<? extends Server>) Class.forName(serverClassName);
        } catch (Exception e) {
            String errorStr = new StringBuilder()
                    .append("Unable to load server type:")
                    .append(type)
                    .append(", class: ")
                    .append(serverClassName)
                    .append("error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcServerException(errorStr);
        }
    }
}
