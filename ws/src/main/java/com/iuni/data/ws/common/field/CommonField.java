package com.iuni.data.ws.common.field;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum CommonField {
    TYPE("type"),
    VK("vk"),
    SID("sid"),
    UID("uid"),;

    CommonField(String realFiled) {
        this.realFiled = realFiled;
    }

    private String realFiled;

    public String getRealFiled() {
        return this.realFiled;
    }
}
