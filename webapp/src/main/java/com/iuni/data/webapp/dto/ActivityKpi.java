package com.iuni.data.webapp.dto;

import java.util.Date;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ActivityKpi {

    private Date date;

    private Integer pv;

    private Integer uv;

    private Map<String, Integer> countMap;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Map<String, Integer> getCountMap() {
        return countMap;
    }

    public void setCountMap(Map<String, Integer> countMap) {
        this.countMap = countMap;
    }
}

