package com.iuni.data;

import com.iuni.data.utils.HttpUtils;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {

    public static final String ROWKEY_1 = "pv144342001600000000";
    public static final String ROWKEY_2 = "cgi144342001600000000";
    public static final String ROWKEY_3 = "click144342001600000000";
    public static final String url = "http://www.abc.com/?a=1&b=2";

    public static void main(String args[]) {

        System.out.println(ROWKEY_1.substring(0, 2) + " - " + ROWKEY_1.substring(2, 15));
        System.out.println(ROWKEY_2.substring(0, 3) + " - " + ROWKEY_2.substring(3, 16));
        System.out.println(ROWKEY_3.substring(0, 5) + " - " + ROWKEY_3.substring(5, 18));

        System.out.println(HttpUtils.getUrlNoParam(HttpUtils.decode(url, "UTF-8")));

    }

}
