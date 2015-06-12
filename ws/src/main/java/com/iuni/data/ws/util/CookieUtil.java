package com.iuni.data.ws.util;

import com.iuni.data.ws.common.CookieKey;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Cookie Util
 * generate cookie, add cookie etc.
 */
public class CookieUtil {

    /**
     * 生成数据上报UV标识Cookie
     * @param time
     * @return
     */
	public static String genUvCookie(Long time){

		StringBuffer buf = new StringBuffer();
		Random random = new Random();

		String cookie;

		// 组合随机数
		String randomStr = "";
		for(int i = 0; i < 3; i++) {
			randomStr += random.nextInt(10);
		}

		String valueB64 = NumBaseConvertor.base10ToX(time + randomStr, 64);
		buf.append(valueB64);
        cookie = buf.toString();

		return cookie;
	}

    /**
     * 新增UV Cookie
     * @param request
     * @param cookieVal
     * @return
     */
    public static Boolean addToCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey, String cookieVal) {
        Boolean status = false;

        if(StringUtils.isNotBlank(cookieVal)) {
            String uvCookieName = cookieKey;
            String serverName = request.getServerName();
            String[] serverNameDetail = serverName.split("\\.");
            String domain = serverName;
            if(null != serverNameDetail && serverNameDetail.length >= 2) {
                domain = serverNameDetail[serverNameDetail.length -2] + "." + serverNameDetail[serverNameDetail.length - 1];
            }

            Cookie uvCookie = new Cookie(uvCookieName, cookieVal);
            uvCookie.setDomain(domain);
            uvCookie.setPath("/");
            uvCookie.setMaxAge(Integer.MAX_VALUE);

            response.addCookie(uvCookie);
            status = true;
        }

        return status;
    }
	
	/**
	 * 获取[min,max]范围随机数
	 * @param min
	 * @param max
	 * @return int 
	 */
	public static int getRandomInRange(int min, int max) {
		Random random = new Random();
        int val = random.nextInt(max)%(max-min+1) + min;
        return val;
	}

}
