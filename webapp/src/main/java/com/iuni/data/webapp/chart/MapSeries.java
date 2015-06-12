package com.iuni.data.webapp.chart;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class MapSeries {

    // series序列组名称
    public String name;

    // series序列组呈现图表类型(line、column、bar等)
    public String type;

    // series序列组的数据为数据类型数组
    public List<AreaData> data;

    // 地图类型
    private String mapType;

    // 是否开启滚轮缩放和拖拽漫游
    private boolean roam;

    private ItemStyle itemStyle;

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

    public List<AreaData> getData() {
        return data;
    }

    public void setData(List<AreaData> data) {
        this.data = data;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public boolean isRoam() {
        return roam;
    }

    public void setRoam(boolean roam) {
        this.roam = roam;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
    }
}
