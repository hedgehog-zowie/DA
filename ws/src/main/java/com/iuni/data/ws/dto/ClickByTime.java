package com.iuni.data.ws.dto;

/**
 * 点击数据返回 - 按时间段统计
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ClickByTime extends PageResult {
    /**
     * 该时间段的开始时间戳
     */
    private long timeStamp;
    /**
     * 点击编码
     */
    private String rTag;
    /**
     * 点击所在URL
     */
    private String url;
    /**
     * 数量
     */
    private long count;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ClickByTime(String rTag, String url, long count){
        this.rTag = rTag;
        this.url = url;
        this.count = count;
    }

    public String getrTag() {
        return rTag;
    }

    public void setrTag(String rTag) {
        this.rTag = rTag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Click{" +
                "rTag='" + rTag + '\'' +
                ", url='" + url + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((ClickByTime)o).getCount() - this.getCount());
    }
}
