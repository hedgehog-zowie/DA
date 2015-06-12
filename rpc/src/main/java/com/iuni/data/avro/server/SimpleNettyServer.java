package com.iuni.data.avro.server;

import com.iuni.data.Context;
import com.iuni.data.avro.common.Constants;
import com.iuni.data.avro.exceptions.RpcServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SimpleNettyServer extends Server {

    private static final Logger logger = LoggerFactory.getLogger(SimpleNettyServer.class);

    @Override
    public void start() throws RpcServerException {
        server = new org.apache.avro.ipc.NettyServer(new Handler(protocol), new InetSocketAddress(Constants.DEFAULT_PORT));
        super.start();
    }

    @Override
    public void configure(Context context) {
        super.configure(context);
    }
}
