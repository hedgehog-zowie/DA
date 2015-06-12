package com.iuni.data.avro.client;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.avro.ProtocolFactory;
import com.iuni.data.avro.common.AvroUtils;
import com.iuni.data.avro.common.Constants;
import com.iuni.data.avro.exceptions.RpcClientException;
import com.iuni.data.avro.exceptions.RpcException;
import com.iuni.data.conf.Configurable;
import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.generic.GenericRequestor;
import org.apache.avro.util.Utf8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class Client implements Configurable {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    protected String name;
    protected Protocol protocol;
    protected Transceiver transceiver;
    protected GenericRequestor requestor;

    protected String host;
    protected int port;
    protected String protopath;

    public Client() {

    }

    public void getData() throws RpcClientException {
        Schema requestEvtSchema;
        Schema requestEvtPvSchema;
        try {
            requestEvtSchema = AvroUtils.parseSchema(this.getClass().getResourceAsStream(Constants.requestEvtPath));
            requestEvtPvSchema = AvroUtils.parseSchema(this.getClass().getResourceAsStream(Constants.requestEvtPvPath));
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("parse schema failed. schema, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }

        GenericRecord evt = new GenericData.Record(requestEvtSchema);
        evt.put("transactionId", new Utf8("123456"));
        evt.put("kpiType", new Utf8("PV"));
        evt.put("startTime", new Utf8("0123456789"));
        evt.put("endTime", new Utf8("9876543210"));
        evt.put("timeType", new Utf8("hh"));

        GenericRecord pvevt = new GenericData.Record(requestEvtPvSchema);
        pvevt.put("evt", evt);

        GenericRecord requestData = new GenericData.Record(protocol.getMessages().get("analyzePv").getRequest());
        requestData.put("req", pvevt);

        Object result = null;
        try {
            result = requestor.request("analyzePv", requestData);
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("request avro server failed, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
        if (result instanceof GenericData.Record) {
            GenericData.Record resultRecord = (GenericData.Record) result;
        }
        logger.debug(result.toString());
    }

    public void close() throws RpcClientException {
        try {
            this.transceiver.close();
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("close connection failed, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcClientException(errorStr);
        }
    }

    @Override
    public void configure(Context context) {
        host = context.getString("host");
        Preconditions.checkNotNull(host, "Host cannot be empty, please specify in configuration file");
        port = context.getInteger("port", Constants.DEFAULT_PORT);
        Preconditions.checkNotNull(port, "Port name cannot be empty, please specify in configuration file");
        if (port <= 0 || port > 65535) {
            port = Constants.DEFAULT_PORT;
            logger.warn("Invalid port specified, initializing client to default capacity of {}", Constants.DEFAULT_PORT);
        }
        protopath = context.getString("protopath", Constants.DEFAULT_AVPR);
        try {
            protocol = ProtocolFactory.create(protopath);
        } catch (RpcException e) {
            logger.error("config client - setBasicInfoForCreate protocol error, protopath is: {}, error msg is: {}", protopath, e.getLocalizedMessage());
        }
    }

    /***************************/
    /****getters and setters****/
    /**
     * ***********************
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
