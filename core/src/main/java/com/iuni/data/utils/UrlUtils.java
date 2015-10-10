package com.iuni.data.utils;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class UrlUtils {

    private static final String httpStart = "http://";
    private static final String invalidEnd = "/";

    /**
     * 根据域名补全URL
     *
     * @param url
     * @param serverName
     * @return
     */
    public static String completeUrl(String url, String serverName) {
        url = url.trim();
        // 补全URL
        if (!url.startsWith(httpStart))
            url = httpStart + serverName + url;
        // 去除最后的/
        if (url.endsWith(invalidEnd))
            url.substring(0, url.length() - 1);
        return url;
    }

    /**
     * 补全URL
     *
     * @param url
     * @return
     */
    public static String completeUrl(String url) {
        url = url.trim();
        if(url.startsWith(httpStart))
            url = url.substring(httpStart.length());
        url = url.replace("//", "/");
        if (url.endsWith(invalidEnd))
            url = url.substring(0, url.length() - 1);
        url = httpStart + url;
        return url;
    }

}
