package com.iuni.data.ws.model;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class QueryWebKpiParam {
    String startTime;
    String endTime;
    String tType;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    @Override
    public String toString() {
        return "QueryWebKpiParam{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tType='" + tType + '\'' +
                '}';
    }
}
