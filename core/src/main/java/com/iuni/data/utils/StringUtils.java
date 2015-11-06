package com.iuni.data.utils;

import com.iuni.data.persist.model.AbstractQueryDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
    private static String quote = "\"";
    public static final String NULL_STR = "-";
    public static final String dateRangeToStr = " - ";

    public static String trimQuotes(String str) {
        str = str.trim();
        int start = 0;
        int end = str.length();
        boolean flag = false;
        if (str.startsWith(quote)) {
            start = 1;
            flag = true;
        }
        if (str.endsWith(quote)) {
            end--;
            flag = true;
        }
        str = str.substring(start, end);
        if (flag) {
            return trimQuotes(str);
        }
        return NULL_STR.equals(str) ? "" : str;
    }

    public static boolean isNullStr(String str) {
        return (NULL_STR.equals(str.trim())) || (isBlank(str.trim()));
    }

    public static Map<String, String> parseCookieStr(String cookieStr) {
        Map<String, String> cookieMap = new HashMap<>();
        String[] cookies = cookieStr.split(";");
        for (String cookie : cookies) {
            String[] cookiePair = cookie.split("=");
            if (cookiePair.length == 2)
                cookieMap.put(cookiePair[0].trim().toLowerCase(), cookiePair[1].trim().toLowerCase());
        }
        return cookieMap;
    }

    public static String getLastSevenDaysRangeString() {
        Date date = new Date();
        return DateUtils.dateToSimpleDateStr(DateUtils.computeStartDate(date, -7), "yyyy/MM/dd") + dateRangeToStr + DateUtils.dateToSimpleDateStr(DateUtils.computeStartDate(date, -1), "yyyy/MM/dd");
    }

//    public static void parseDateRangeString(AbstractQueryDto queryDto) {
//        String[] strs = queryDto.getDateRangeString().split(dateRangeToStr);
//        queryDto.setStartDateStr(strs[0].trim());
//        queryDto.setEndDateStr(strs[1].trim());
//    }

    public static String getObjectStringValue(final Object obj) {
        if (obj == null) {
            return "";
        } else {
            String value = String.valueOf(obj);
            if(isBlank(value) || "null".equalsIgnoreCase(value)){
                return "";
            }
            return String.valueOf(obj);
        }
    }

}
