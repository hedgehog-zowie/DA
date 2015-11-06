package com.iuni.data.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Cookie Util
 * generate cookie, add cookie etc.
 */
public class CookieUtil {

    private static final String UNDERLINE = "_";

    /**
     * 生成UV Cookie
     * @param time
     * @return
     */
	public static String genUvCookie(String ip, Long time){
        if("0:0:0:0:0:0:0:1".equals(ip))
            ip = "127.0.0.1";

		StringBuffer buf = new StringBuffer();
		Random random = new Random();

		String cookie = null;

        String[] ipDetail = ip.split("\\.");
        if(null == ipDetail || ipDetail.length <= 0) {
            return cookie;
        }
        // 获取FULL IP
        String fullIp = "";
        for(String ipSec : ipDetail) {
            if(ipSec.length() < 3) {
                for(int i = 0; i < 3 - ipSec.length(); i++) {
                    fullIp += "0";
                }
            }
            fullIp += ipSec;
        }
        // 组合Full IP、current time
        String fullIpB64 = NumBaseConvertor.base10ToX(fullIp, 64);
        buf.append(fullIpB64 + UNDERLINE);

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
     * 解析UV Cookie
     * @param uvCookie
     * @return
     * @throws GeneralSecurityException
     */
    public static Map<String, Object> getDataFromUvCookie(String uvCookie)
            throws GeneralSecurityException {
        Map<String, Object> data = new HashMap<>();

        String[] cookieDetail = uvCookie.split("\\_");
        if(null != cookieDetail && cookieDetail.length == 2) {
            String fullIp = NumBaseConvertor.xToBase10(cookieDetail[0], 64);
            if(fullIp.length() > 9) {
                String ip1 = fullIp.substring(fullIp.length() - 3, fullIp.length());
                String ip2 = fullIp.substring(fullIp.length() - 6, fullIp.length() - 3);
                String ip3 = fullIp.substring(fullIp.length() - 9, fullIp.length() - 6);
                String ip4 = fullIp.substring(0, fullIp.length() - 9);
                fullIp = ip4 + "." + ip3 + "." + ip2 + "." + ip1;
                data.put("ip", fullIp);
            }
            String remains = NumBaseConvertor.xToBase10(cookieDetail[1], 64);
            if(StringUtils.isNotBlank(remains)) {
                String time = remains.substring(0, remains.length()-3);
                String randomStr = remains.substring(remains.length()-3, remains.length());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.parseLong(time));
                data.put("time", cal.getTime());
                data.put("randomNumber", randomStr);
            }

        }

        return data;
    }

    /**
     * 新增Cookie
     * @param request
     * @param response
     * @param cookieKey
     * @param cookieVal
     * @return
     */
    public static Boolean addToCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey, String cookieVal) {
        return addToCookie(request, response, cookieKey, cookieVal, -1);
    }

    /**
     * 新增Cookie
     * @param request
     * @param response
     * @param cookieKey
     * @param cookieVal
     * @param expire
     * @return
     */
    public static Boolean addToCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey, String cookieVal, Integer expire) {
        Boolean status = false;

        if(StringUtils.isNotBlank(cookieVal)) {
            String cookieName = cookieKey;
            String serverName = request.getServerName();
            String[] serverNameDetail = serverName.split("\\.");
            String domain = serverName;
            if(null != serverNameDetail && serverNameDetail.length >= 2) {
                domain = serverNameDetail[serverNameDetail.length -2] + "." + serverNameDetail[serverNameDetail.length - 1];
            }

            Cookie cookie = new Cookie(cookieName, cookieVal);
            cookie.setDomain(domain);
            cookie.setPath("/");
            cookie.setMaxAge(expire);

            response.addCookie(cookie);
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
