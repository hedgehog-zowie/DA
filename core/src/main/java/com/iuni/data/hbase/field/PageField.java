package com.iuni.data.hbase.field;

/**
 * 页面访问数据上报字段
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum PageField {
    /**
     * 页面URL
     */
    url("s1"),
    /**
     * 渠道ID
     */
    @Deprecated
    adId("s2"),
    /**
     * 上游页
     */
    referrer("s3"),
    /**
     * 执行时间，可以不上报
     */
    execTime("s4");

    private String realFiled;

    PageField(String realFiled){
        this.realFiled = realFiled;
    }

    public String getRealFiled(){
        return this.realFiled;
    }
}
