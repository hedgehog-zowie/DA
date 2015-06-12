package com.iuni.data.ws.dto;

/**
 * UV - 按时间段统计
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class UVByTime extends PageResult{
    /**
     * 该时间段的开始时间戳
     */
    private long timeStamp;
    /**
     * URL
     */
    private String url;
    /**
     * 渠道编号
     */
    private String adId;
    /**
     * 数量
     */
    private long uv;

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

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((UVByTime) o).getUv() - this.getUv());
    }
}
