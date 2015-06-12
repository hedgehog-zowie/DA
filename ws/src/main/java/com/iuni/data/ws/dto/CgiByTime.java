package com.iuni.data.ws.dto;

/**
 * Cgi数据返回 - 按时间段统计
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class CgiByTime extends PageResult {
    /**
     * 该时间段的开始时间戳
     */
    private long timeStamp;
    /**
     * cgi 名称
     */
    private String cgi;
    /**
     * 状态
     */
    private String state;
    /**
     * 数量
     */
    private long count;
    /**
     * 平均时间
     */
    private double averageTime;

    public CgiByTime(String cgi, String state, long count, double averageTime){
        this.cgi = cgi;
        this.state = state;
        this.count = count;
        this.averageTime = averageTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = averageTime;
    }

    @Override
    public String toString() {
        return "Cgi{" +
                "cgi='" + cgi + '\'' +
                ", state='" + state + '\'' +
                ", count=" + count +
                ", averageTime=" + averageTime +
                '}';
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((CgiByTime)o).getCount() - this.getCount());
    }
}
