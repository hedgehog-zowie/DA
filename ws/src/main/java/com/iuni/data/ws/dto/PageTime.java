package com.iuni.data.ws.dto;

/**
 * 页面加载时间返回
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PageTime extends PageResult {
    /**
     * 页面URL
     */
    private String url;
    /**
     * 执行时间
     */
    private double execTime;

    public PageTime(String url, double execTime){
        this.url = url;
        this.execTime = execTime;
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
        return (int) (((PageTime)o).getExecTime() - this.getExecTime());
    }
}
