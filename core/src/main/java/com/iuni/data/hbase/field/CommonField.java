package com.iuni.data.hbase.field;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum CommonField {
    TYPE("type"),
    VK("vk"),
    IP("ip"),
    SID("sid"),
    UID("uid"),
    ADID("adId"), ;
    ;

    CommonField(String realFiled) {
        this.realFiled = realFiled;
    }

    private String realFiled;

    public String getRealFiled() {
        return this.realFiled;
    }
}
