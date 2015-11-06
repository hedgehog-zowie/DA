package com.iuni.data.webapp.common;

/**
 * 统计日期类型枚举
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum DateStyle {

    YYYYMMDD("YYYY/MM/DD", "按天"),
    YYYYMM("YYYY/MM", "按月"),
    YYYYIW("YYYY/IW", "按周");

    /**
     * 模式
     */
    private String dateStyleStr;
    /**
     * 含义
     */
    private String dateStyleName;

    DateStyle(String dateStyleStr, String dateStyleName) {
        this.dateStyleStr = dateStyleStr;
        this.dateStyleName = dateStyleName;
    }

    public String getDateStyleStr() {
        return dateStyleStr;
    }

    public void setDateStyleStr(String dateStyleStr) {
        this.dateStyleStr = dateStyleStr;
    }

    public String getDateStyleName() {
        return dateStyleName;
    }

    public void setDateStyleName(String dateStyleName) {
        this.dateStyleName = dateStyleName;
    }

}
