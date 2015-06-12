package com.iuni.data.common;

import java.util.Calendar;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum TType {

    YEAR(Calendar.YEAR, "yyyy"),
    MONTH(Calendar.MONTH, "MM"),
    DAY(Calendar.DATE, "dd"),
    HOUR(Calendar.HOUR, "HH"),
    MINUTE(Calendar.MINUTE, "mm"),
    SECOND(Calendar.SECOND, "ss"),
    ;

    private final int value;
    private final String pattern;

    TType(int value, String type){
        this.value = value;
        this.pattern = type;
    }

    public int getValue() {
        return value;
    }

    public String getPattern(){
        return pattern;
    }
}
