package com.iuni.data.ws.dto;

import com.iuni.data.ws.common.QueryType;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class WebKpi extends PageResult {

    /**
     * 指标类型
     */
    private QueryType kpiType;

    /**
     * 时间
     */
    private long timeStamp;

    /**
     * 统计类型，按日(dd)、时(HH)、分(MM)等
     */
    private String timeType;

    /**
     * url 页面地址
     */
    private String url;

    /**
     * adId 渠道号
     */
    private String adId;

    /**
     * rTag标识，页面点击编号
     */
    private String rTag;

    /**
     * 页面CGI请求
     */
    private String cgi;

    /**
     * 页面CGI状态
     */
    private String state;

    /**
     * 值，一般指数量，当类型为execTime时，该值表示ms数
     */
    private int count;

    public QueryType getKpiType() {
        return kpiType;
    }

    public void setKpiType(QueryType kpiType) {
        this.kpiType = kpiType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getrTag() {
        return rTag;
    }

    public void setrTag(String rTag) {
        this.rTag = rTag;
    }

    public String getCgi() {
        return cgi;
    }

    public void setCgi(String cgi) {
        this.cgi = cgi;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(PageResult o) {
        return 0;
    }



}
