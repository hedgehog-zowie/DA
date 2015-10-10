package com.iuni.data.webapp.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StatusResponse {

    public static final String STATUS_CODE_OK = "200";
    public static final String STATUS_CODE_ERROR = "300";
    public static final String STATUS_CODE_TIMEOUT = "301";

    /**
     * 关闭TAB
     */
    public static final String CALL_BACK_TYPE_CLOSE = "closeCurrent";
    /**
     *
     */
    public static final String CALL_BACK_TYPE_FORWORD = "forward";

    private String statusCode;
    private String message;
    private String callbackType;
    private String navTabId;
    private String rel;
    private String forwardUrl;

    public StatusResponse(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public StatusResponse(String statusCode, String message, String callbackType) {
        this.statusCode = statusCode;
        this.message = message;
        this.callbackType = callbackType;
    }

    public StatusResponse(String statusCode, String message, String callbackType, String navTabId, String rel, String forwardUrl) {
        this.statusCode = statusCode;
        this.message = message;
        this.callbackType = callbackType;
        this.navTabId = navTabId;
        this.rel = rel;
        this.forwardUrl = forwardUrl;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }
}
