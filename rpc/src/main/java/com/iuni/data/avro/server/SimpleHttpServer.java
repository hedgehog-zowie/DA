package com.iuni.data.avro.server;

import com.iuni.data.Context;
import com.iuni.data.avro.exceptions.RpcServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SimpleHttpServer extends Server {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

    @Override
    public void start() throws RpcServerException {
        try {
            server = new org.apache.avro.ipc.HttpServer(new Handler(protocol), port);
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("start avro http server failed, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcServerException(errorStr);
        }
        super.start();
    }

    @Override
    public void configure(Context context) {
        super.configure(context);
    }
}
