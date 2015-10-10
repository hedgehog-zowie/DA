package com.iuni.data.webapp.chart;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Series {
    // series序列组名称
    public String name;

    // series序列组呈现图表类型(line、column、bar等)
    public String type;

    // series序列组的数据为数据类型数组
    public List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
