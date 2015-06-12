package com.iuni.data.avro.server;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.avro.ProtocolFactory;
import com.iuni.data.avro.common.Constants;
import com.iuni.data.avro.exceptions.RpcException;
import com.iuni.data.avro.exceptions.RpcServerException;
import com.iuni.data.conf.Configurable;
import org.apache.avro.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class Server implements Configurable {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    protected String name;
    protected Integer port;
    protected String protopath;
    protected Protocol protocol;
    protected org.apache.avro.ipc.Server server;

    public void start() throws RpcServerException {
        server.start();
    }

    public void stop() throws RpcServerException {
        try{
            server.close();
        }catch (Exception e){
            String errorStr = new StringBuilder()
                    .append("stop avro http server failed, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcServerException(errorStr);
        }
    }

    @Override
    public void configure(Context context) {
        port = context.getInteger("port", Constants.DEFAULT_PORT);
        Preconditions.checkNotNull(port, "Port name cannot be empty, please specify in configuration file");
        if(port <=0 || port > 65535) {
            port = Constants.DEFAULT_PORT;
            logger.warn("Invalid port specified, initializing client to default capacity of {}", Constants.DEFAULT_PORT);
        }
        protopath = context.getString("protopath", Constants.DEFAULT_AVPR);
        try {
            protocol = ProtocolFactory.create(protopath);
        } catch (RpcException e) {
            logger.error("config server - setBasicInfoForCreate protocol error, protopath is: {}, error msg is: {}", protopath, e.getLocalizedMessage());
        }
    }

    /***************************/
    /****getters and setters****/
    /***************************/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Protocol getProtocol(){
        return protocol;
    }
    public void setProtocol(Protocol protocol){
        this.protocol = protocol;
    }
}
