package com.iuni.data.webapp.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ResultOfAjax {

    public static String CODE_SUCCEED = "0";
    public static String CODE_FAILED = "-1";

    private String code;
    private String msg;

    public ResultOfAjax() {
    }

    public ResultOfAjax(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
