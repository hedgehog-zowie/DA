package com.iuni.data.persist.model;

import org.springframework.util.StringUtils;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class AbstractQueryDto {

    protected static final String dateRangeToStr = "-";

    /**
     * 时间段
     */
    private String dateRangeString;
    /**
     * 开始时间
     */
    private String startDateStr;
    /**
     * 结束时间
     */
    private String endDateStr;

    public String getDateRangeString() {
        return dateRangeString;
    }

    public void setDateRangeString(String dateRangeString) {
        this.dateRangeString = dateRangeString;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    /**
     * 解析时间区间
     */
    public void parseDateRangeString() {
        if (!StringUtils.isEmpty(dateRangeString)) {
            String[] strs = dateRangeString.split(dateRangeToStr);
            setStartDateStr(strs[0].trim());
            setEndDateStr(strs[1].trim());
        }
    }

}
