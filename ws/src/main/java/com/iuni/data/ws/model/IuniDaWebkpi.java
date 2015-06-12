package com.iuni.data.ws.model;

import java.io.Serializable;
import java.util.Date;

public class IuniDaWebkpi implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer newUv;

	private Integer pv;

	private String time;

	private Integer totalJump;

	private Integer totalTime;

	private String ttype;

	private Integer uv;

	private Integer vv;

    public IuniDaWebkpi() {
    }

    public Integer getNewUv() {
        return newUv;
    }

    public void setNewUv(Integer newUv) {
        this.newUv = newUv;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTotalJump() {
        return totalJump;
    }

    public void setTotalJump(Integer totalJump) {
        this.totalJump = totalJump;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getVv() {
        return vv;
    }

    public void setVv(Integer vv) {
        this.vv = vv;
    }

    @Override
    public String toString() {
        return "IuniDaWebkpi{" +
                "newUv=" + newUv +
                ", pv=" + pv +
                ", time=" + time +
                ", totalJump=" + totalJump +
                ", totalTime=" + totalTime +
                ", ttype='" + ttype + '\'' +
                ", uv=" + uv +
                ", vv=" + vv +
                '}';
    }
}