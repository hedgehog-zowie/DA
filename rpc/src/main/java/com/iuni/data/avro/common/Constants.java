package com.iuni.data.avro.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Constants {

    /**
     * 默认主机
     */
    public static final String DEFAULT_HOST = "localhost";

    /**
     * 默认端口
     */
    public static final int DEFAULT_PORT = 8088;

    /**
     * 默认avro模式地址
     */
    public static final String DEFAULT_AVPR = "/avsc/analyzeData.json";

    public static final String requestEvtPath = "/avsc/requestEvt.json";
    public static final String requestEvtIpPath = "/avsc/requestEvtIp.json";
    public static final String requestEvtPvPath = "/avsc/requestEvtPv.json";
    public static final String requestEvtUvPath = "/avsc/requestEvtUv.json";
    public static final String requestEvtAreaPath = "/avsc/requestEvtArea.json";
    public static final String requestEvtUserPagePath = "/avsc/requestEvtUserPage.json";

    public static final String locationPath = "/avsc/Location.json";
    public static final String kpiPvPath = "/avsc/KpiPV.json";
    public static final String kpiUvPath = "/avsc/KpiUV.json";
    public static final String kpiIpPath = "/avsc/KpiIP.json";
    public static final String kpiAreaPath = "/avsc/KpiArea.json";
    public static final String kpiUserPagePath = "/avsc/KpiUserPage.json";

    public static final String responseEvtPath = "/avsc/responseEvt.json";
    public static final String responseEvtIpPath = "/avsc/responseEvtIp.json";
    public static final String responseEvtPvPath = "/avsc/responseEvtPv.json";
    public static final String responseEvtUvPath = "/avsc/responseEvtUv.json";
    public static final String responseEvtAreaPath = "/avsc/responseEvtArea.json";
    public static final String responseEvtUserPagePath = "/avsc/responseEvtUserPage.json";

    /**
     * 获取默认地址
     * @return
     */
    public static final String getDefaultAddress(){
        return new StringBuilder().append("http://").append(DEFAULT_HOST).append(":").append(DEFAULT_PORT).toString();
    }


}
