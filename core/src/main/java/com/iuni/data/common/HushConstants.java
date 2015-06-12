package com.iuni.data.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class HushConstants {
    private static final String PAGE1 = "www.iuni.com/page1.html";
    private static final String PAGE2 = "www.iuni.com/page2.html";
    private static final String PAGE3 = "www.iuni.com/page3.html";
    private static final String PAGE4 = "www.iuni.com/page4.html";
    private static final Map<String, String> hushMap = new HashMap();

    static {
        hushMap.put("www.iuni.com/page1.html", "PAGE1");
        hushMap.put("www.iuni.com/page2.html", "PAGE2");
        hushMap.put("www.iuni.com/page3.html", "PAGE3");
        hushMap.put("www.iuni.com/page4.html", "PAGE4");
    }

    public static String getHush(String url) {
        return (String) hushMap.get(url);
    }
}
