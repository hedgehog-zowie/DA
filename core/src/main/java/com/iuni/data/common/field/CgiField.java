package com.iuni.data.common.field;

/**
 * CGI数据上报字段
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum CgiField {
    /**
     * cgi请求的URL地址
     */
    url("s1"),
    /**
     * cgi请求状态，state为succ 或者 fail
     */
    state("s2"),
    /**
     * cgi请求所用时间
     */
    time("s3");

    private String realFiled;

    CgiField(String realFiled) {
        this.realFiled = realFiled;
    }

    public String getRealFiled() {
        return this.realFiled;
    }
}
