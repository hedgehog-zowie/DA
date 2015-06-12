package com.iuni.data.ws.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum QueryType {
    PV("pv"),
    UV("uv"),
    CLICK("click"),
    CGI("cgi"),
    PAGETIME("pageExecTime"),
    PV5MIN("pv5min"),
    ;

    private String name;

    QueryType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
