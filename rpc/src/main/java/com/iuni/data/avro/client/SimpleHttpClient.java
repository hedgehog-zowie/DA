package com.iuni.data.avro.client;

import com.iuni.data.Context;
import com.iuni.data.avro.exceptions.RpcException;
import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.generic.GenericRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SimpleHttpClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpClient.class);

    public SimpleHttpClient() throws RpcException {

    }

    @Override
    public void configure(Context context) {
        super.configure(context);
        StringBuilder sb = new StringBuilder().append("http://").append(host).append(":").append(port);
        URL url = null;
        try {
            url = new URL(sb.toString());
        } catch (MalformedURLException e) {
            logger.error("config client - setBasicInfoForCreate url error, url address is: {}, error msg is: {}", sb.toString(), e.getLocalizedMessage());
        }
        this.transceiver = new HttpTransceiver(url);
        try {
            requestor = new GenericRequestor(this.protocol, transceiver);
        } catch (IOException e) {
            logger.error("config client - setBasicInfoForCreate url error, url address is: {}, error msg is: {}", sb.toString(), e.getLocalizedMessage());
        }
    }

}
