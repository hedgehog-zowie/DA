package com.iuni.data.ws.dto;

/**
 * 页面加载时间 - 按时间段统计
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PageTimeByTime extends PageResult {
    /**
     * 该时间段的开始时间戳
     */
    private long timeStamp;
    /**
     * 页面URL
     */
    private String url;
    /**
     * 执行时间
     */
    private double execTime;

    public PageTimeByTime(String url, double execTime){
        this.url = url;
        this.execTime = execTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getExecTime() {
        return execTime;
    }

    public void setExecTime(double execTime) {
        this.execTime = execTime;
    }

    @Override
    public String toString() {
        return "PageTime{" +
                "url='" + url + '\'' +
                ", execTime=" + execTime +
                '}';
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((PageTimeByTime)o).getExecTime() - this.getExecTime());
    }
}
