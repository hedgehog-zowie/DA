package com.iuni.data.common;

import org.apache.commons.lang.StringUtils;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class IuniStringUtils extends org.apache.commons.lang.StringUtils {
    private static String quote = "\"";
    public static final String NULL_STR = "-";

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
}
