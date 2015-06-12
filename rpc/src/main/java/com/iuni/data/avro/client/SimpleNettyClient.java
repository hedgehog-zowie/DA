package com.iuni.data.avro.client;

import com.iuni.data.Context;
import com.iuni.data.avro.exceptions.RpcClientException;
import org.apache.avro.Protocol;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.generic.GenericRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SimpleNettyClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpClient.class);

    public SimpleNettyClient(){
//        this(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT, ProtocolFactory.setBasicInfoForCreate());
    }

    public SimpleNettyClient(String host, Integer port, Protocol protocol) throws RpcClientException {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            String errorStr = new StringBuilder()
                    .append("setBasicInfoForCreate netty client failed: unknown host, host is: ")
                    .append(host)
                    .append("error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, port);
        initNettyClient(inetSocketAddress, protocol);
    }

    public SimpleNettyClient(InetAddress inetAddress, int port, Protocol protocol) throws RpcClientException {
        this(new InetSocketAddress(inetAddress, port), protocol);
    }

    public SimpleNettyClient(InetSocketAddress inetSocketAddress, Protocol protocol) throws RpcClientException {
        initNettyClient(inetSocketAddress, protocol);
    }

    /**
     * 初始化netty client
     *
     * @param inetSocketAddress
     * @param protocol
     * @throws com.iuni.data.avro.exceptions.RpcClientException
     */
    private void initNettyClient(InetSocketAddress inetSocketAddress, Protocol protocol) throws RpcClientException {
        this.protocol = protocol;
        try {
            transceiver = new NettyTransceiver(inetSocketAddress);
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("setBasicInfoForCreate netty transceiver failed, host is: ")
                    .append(inetSocketAddress.getHostString())
                    .append(", port is: ")
                    .append(inetSocketAddress.getPort())
                    .append("error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
        try {
            requestor = new GenericRequestor(protocol, transceiver);
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("setBasicInfoForCreate requestor failed, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
    }

    @Override
    public void configure(Context context) {
        super.configure(context);
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            logger.error("config client - setBasicInfoForCreate url error, host is: {}, error msg is: {}", host, e.getLocalizedMessage());
        }
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, port);
        try {
            transceiver = new NettyTransceiver(inetSocketAddress);
        } catch (IOException e) {
            logger.error("config client - setBasicInfoForCreate url error, host is: {}, port is: {}", host, port);
            logger.error("error msg is: {}", e.getLocalizedMessage());
        }
        try {
            requestor = new GenericRequestor(protocol, transceiver);
        } catch (IOException e) {
            logger.error("config client - setBasicInfoForCreate url error, host is: {}, port is: {}", host, port);
            logger.error("error msg is: {}", e.getLocalizedMessage());
        }
    }

}
