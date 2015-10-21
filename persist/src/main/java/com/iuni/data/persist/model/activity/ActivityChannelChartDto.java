package com.iuni.data.persist.model.activity;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ActivityChannelChartDto {

    private String name;
    private Integer value;

    public ActivityChannelChartDto(){}

    public ActivityChannelChartDto(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
