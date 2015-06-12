package com.iuni.data.ws.common;

/**
 * 返回码
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ReturnCode {
    /**
     * 成功
     */
    public static final String SUCCESS = "000000";
    public static final String SUCCESS_MSG = "成功";
    /**
     * 参数错误
     */
    public static final String ERROR_PARAM_NO_DATA = "000001";
    public static final String ERROR_PARAM_NO_DATA_MSG = "无数据";
    public static final String ERROR_PARAM = "000002";
    public static final String ERROR_PARAM_MSG_PAGE = "pv类型数据上报参数错误";
    public static final String ERROR_PARAM_MSG_CLICK = "click类型数据上报参数错误";
    public static final String ERROR_PARAM_MSG_CGI = "cgi类型数据上报参数错误";
    /**
     * 类型错误
     */
    public static final String ERROR_TYPE = "100001";
    public static final String ERROR_TYPE_MSG = "类型错误";
    /**
     * 系统错误
     */
    public static final String ERROR_SYSTEM = "200001";
    public static final String ERROR_SYSTEM_MSG = "系统错误";

    public static String Code;
    public static String Msg;

    public ReturnCode(String Code, String Msg) {
        this.Code = Code;
        this.Msg = Msg;
    }

    public String getCode() {
        return this.Code;
    }

    public String getMsg() {
        return this.Msg;
    }

}
