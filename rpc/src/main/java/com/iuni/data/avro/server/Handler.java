package com.iuni.data.avro.server;

import com.iuni.data.avro.common.AvroUtils;
import com.iuni.data.avro.common.Constants;
import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.generic.GenericResponder;
import org.apache.avro.util.Utf8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zowie
 *         Email:   hedgehog.zowie@gmail.com
 */
public class Handler extends GenericResponder {

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    private static final String ANALYZE_PV = "analyzePv";
    private static final String ANALYZE_UV = "analyzeUv";
    private static final String ANALYZE_IV = "analyzeIp";
    private static final String ANALYZE_AREA = "analyzeArea";
    private static final String ANALYZE_USER_PAGE = "analyzeUserPage";

    public Handler(Protocol protocol) {
        super(protocol);
    }

    @Override
    public Object respond(Protocol.Message message, Object request) throws Exception {
        GenericRecord requestData = (GenericRecord) request;
        GenericRecord reMessage = null;
        if (ANALYZE_PV.equals(message.getName())) {
            reMessage = responsePv(requestData);
            logger.info(reMessage.toString());
        }
        return reMessage;
    }

    private GenericRecord responsePv(GenericRecord requestData) throws IOException {
        GenericRecord reMessage = null;
        Object req = requestData.get("req");
        //  TODO get real data
        //取得返回值的类型
        reMessage = new GenericData.Record(super.getLocal().getMessages().get(ANALYZE_PV).getResponse());
        //直接构造回复

        Schema kpipvSchema = AvroUtils.parseSchema(this.getClass().getResourceAsStream(Constants.kpiPvPath));
        GenericRecord kpipv1 = new GenericData.Record(kpipvSchema);
        kpipv1.put("time", new Utf8("0123456789"));
        kpipv1.put("timeType", new Utf8("hh"));
        kpipv1.put("num", (long) 10000);

        GenericRecord kpipv2 = new GenericData.Record(kpipvSchema);
        kpipv2.put("time", new Utf8("012"));
        kpipv2.put("timeType", new Utf8("hh"));
        kpipv2.put("num", (long) 10001);

        Schema responseEvtSchema = AvroUtils.parseSchema(this.getClass().getResourceAsStream(Constants.responseEvtPath));
        GenericRecord responseEvt = new GenericData.Record(responseEvtSchema);
        responseEvt.put("transactionId", new Utf8("123456"));
        responseEvt.put("resultCode", new Utf8("R00000"));
        responseEvt.put("description", new Utf8("haha test"));

        Schema responseEvtPvSchema = AvroUtils.parseSchema(this.getClass().getResourceAsStream(Constants.responseEvtPvPath));
        GenericRecord responseEvtPv = new GenericData.Record(responseEvtPvSchema);
        responseEvtPv.put("evt", responseEvt);
        List<GenericRecord> pvdatalist = new ArrayList<GenericRecord>();
        pvdatalist.add(kpipv1);
        pvdatalist.add(kpipv2);
        responseEvtPv.put("data", pvdatalist);

        reMessage.put("res", responseEvtPv);

        return reMessage;
    }

}
