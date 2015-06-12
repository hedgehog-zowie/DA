package com.iuni.data.common;

/**
 * 上报数据类型
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum DataType {

    PAGE("pv"),
    CLICK("click"),
    CGI("cgi");

    private String name;

    DataType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
