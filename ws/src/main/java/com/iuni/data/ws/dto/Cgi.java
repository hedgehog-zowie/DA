package com.iuni.data.ws.dto;

/**
 * Cgi数据返回
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Cgi extends PageResult {
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

    public Cgi(String cgi, String state, long count, double averageTime){
        this.cgi = cgi;
        this.state = state;
        this.count = count;
        this.averageTime = averageTime;
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
        return (int) (((Cgi)o).getCount() - this.getCount());
    }
}
