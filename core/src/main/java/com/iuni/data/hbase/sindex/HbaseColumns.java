package com.iuni.data.hbase.sindex;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum HbaseColumns {
    COLUMN_HOST("host"),
    COLUMN_TIME("timestamp"),
    COLUMN_ADID("adId"),
    COLUMN_METHOD("met"),
    COLUMN_REQUEST_URL("req"),
    COLUMN_PROTOCOL_TYPE("pro"),
    COLUMN_HTTP_REFERER("ref"),
    COLUMN_COUNTRY("country"),
    COLUMN_AREA("area"),
    COLUMN_REGION("region"),
    COLUMN_CITY("city"),
    COLUMN_COUNTY("county"),
    COLUMN_ISP("isp"),
    COLUMN_USER("user"),
    COLUMN_VK("vk"),
    COLUMN_SID("sid"),
    ;

    private String name;

    private HbaseColumns(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
