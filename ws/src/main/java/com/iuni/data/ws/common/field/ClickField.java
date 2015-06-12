package com.iuni.data.ws.common.field;

/**
 * 点击数据上报字段
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ClickField {
    /**
     * 点击编码
     */
    rTag("s1"),
    /**
     * 点击所在页面URL
     */
    url("s2"),
    /**
     * 渠道
     */
    adId("s3"),
    ;

    private String realFiled;

    ClickField(String realFiled){
        this.realFiled = realFiled;
    }

    public String getRealFiled(){
        return this.realFiled;
    }
}
