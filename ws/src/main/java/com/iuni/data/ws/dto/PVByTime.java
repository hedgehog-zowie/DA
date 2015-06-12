package com.iuni.data.ws.dto;

/**
 * PV - 指定URL、AdId 按时间段统计
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PVByTime extends PageResult{
    /**
     * 该时间段的开始时间戳
     */
    private long timeStamp;
    /**
     * 数量
     */
    private long pv;

    public PVByTime(long timeStamp, long pv) {
        this.timeStamp = timeStamp;
        this.pv = pv;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((PVByTime)o).getPv() - this.getPv());
    }
}
