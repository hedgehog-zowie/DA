package com.iuni.data.avro;

import com.iuni.data.avro.common.AvroUtils;
import com.iuni.data.avro.common.Constants;
import com.iuni.data.avro.exceptions.RpcException;
import org.apache.avro.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zowie
 *         Email:   hedgehog.zowie@gmail.com
 */
public class ProtocolFactory {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolFactory.class);

    public static Protocol create() throws RpcException {
        String avprPath = DefaultServerFactory.class.getResource(Constants.DEFAULT_AVPR).getPath();
        return create(avprPath);
    }

    public static Protocol create(String protopath) throws RpcException {
        if(Constants.DEFAULT_AVPR.equals(protopath)){
            try {
                // request
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.requestEvtPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.requestEvtPvPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.requestEvtUvPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.requestEvtIpPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.requestEvtAreaPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.requestEvtUserPagePath));
                // kpisConstants
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.kpiPvPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.kpiUvPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.kpiIpPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.locationPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.kpiAreaPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.kpiUserPagePath));
                // responseConstants
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.responseEvtPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.responseEvtPvPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.responseEvtUvPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.responseEvtIpPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.responseEvtAreaPath));
                AvroUtils.parseSchema(ProtocolFactory.class.getResourceAsStream(Constants.responseEvtUserPagePath));
            } catch (IOException e) {
                String errorStr = new StringBuilder()
                        .append("parse schema failed, error msg: ")
                        .append(e.getLocalizedMessage())
                        .toString();
                logger.error(errorStr);
                throw new RpcException(errorStr);
            }
        }
        return create(ProtocolFactory.class.getResourceAsStream(protopath));
    }

    public static Protocol create(File file) throws RpcException {
        try {
            return Protocol.parse(file);
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("parse protocol from file error, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcException(errorStr);
        }
    }

    public static Protocol create(InputStream inputStream) throws RpcException {
        try {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = inputStream.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
//            return setBasicInfoForCreate(out.toString());
            return Protocol.parse(AvroUtils.resolveSchema(out.toString()));
        } catch (IOException e) {
            String errorStr = new StringBuilder()
                    .append("parse protocol from file error, error msg: ")
                    .append(e.getLocalizedMessage())
                    .toString();
            logger.error(errorStr);
            throw new RpcException(errorStr);
        }
    }

}
