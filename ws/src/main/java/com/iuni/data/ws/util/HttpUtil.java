package com.iuni.data.ws.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP工具类
 */
public final class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	private static int TIME_OUT_CONNECTION = 30000;  // 30秒  设置连接超时时间(单位毫秒)
    private static int TIME_OUT_SOTIMEOUT = 120000;  // 120秒 设置读数据超时时间(单位毫秒)
    
	public static final String UTF8 = "utf-8";            // 字符编码 UTF-8
    
	private HttpUtil() {

	}

	/**
	 * @Description: 获取请求上下文中的ip地址（127.0.0.1等内部IP未过滤）
	 * @return String
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Cluster-Client-Ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取请求上下文中的真实IP地址
	 * @param request
	 * @return String
	 */
	public static String getRealIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if(StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP
			int index = ip.indexOf(',');
			if (index > -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} 
		
		ip = request.getHeader("Proxy-Client-IP");
		if(StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("WL-Proxy-Client-IP");
		if(StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("HTTP_CLIENT_IP");
		if(StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("X-Cluster-Client-IP");
		if(StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		return request.getRemoteAddr();
	}
	
	/**
	 * 获得发起请求的ID地址
	 * 
	 * @Title: getRequestIP
	 * @return String 返回类型
	 */
	public static String getRequestIP(javax.servlet.http.HttpServletRequest req) {

		String ip = req.getHeader("X-Cluster-Client-Ip");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将IP地址转换成10进制整数
	 *
	 * @param strIp
	 * @return
	 */
	public static String convertIp(String strIp) {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return String.valueOf((ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8)+ ip[3]);
	}

	/**
	 * Initiates the request
	 *
	 * @param reqUrl
	 * @return
	 */
	public static String postRequest(String reqUrl) {
		DataInputStream in = null;
		StringBuilder out = new StringBuilder();
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = new DataInputStream(connection.getInputStream());
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.append(new String(buffer, 0, count));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					in = null;
				}
			}
		}
		return out.toString();
	}

	/**
	 * Post Request
	 * @param data
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String post4Data(String data, String url, String charset, String contentType) {
		String result = "";
		CloseableHttpClient hc = null;
		CloseableHttpResponse httpResponse = null;

		try {
			hc = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			if(StringUtils.isBlank(contentType)) {
				contentType = "text/xml; charset=UTF-8";
			}
			Header contentTypeHeader = new BasicHeader("Content-Type",contentType);
			httpPost.addHeader(contentTypeHeader);
			HttpEntity reqEntity = new StringEntity(data, charset);
			httpPost.setEntity(reqEntity);
			httpResponse = hc.execute(httpPost);
			HttpEntity resEntity = httpResponse.getEntity();
			if(null != resEntity) {
				result = EntityUtils.toString(resEntity, charset);
			}
		} catch (IOException e) {
			logger.error("HttpUtil.postRequest found IOException.", e);
		} finally {
			try {
				if(null != httpResponse) {
					httpResponse.close();
				}
				if(null != hc) {
					hc.close();
				}
			} catch (IOException e) {
				logger.error("HttpUtil.postRequest found IOException.", e);
			}
		}

		return result;
	}

	/**
	 * Get Request
	 * @param url
	 * @param charset
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String get4Data(String url, String charset) {
		String result = null;
		CloseableHttpClient hc = null;
		CloseableHttpResponse httpResponse = null;

		try {
			hc = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig reqConfig = RequestConfig.custom()
					.setConnectTimeout(TIME_OUT_CONNECTION)
					.setSocketTimeout(TIME_OUT_SOTIMEOUT).build();
			httpGet.setConfig(reqConfig);
			httpGet.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset);
			httpResponse = hc.execute(httpGet);
			HttpEntity httpEntry = httpResponse.getEntity();
			if(null != httpEntry) {
				result = EntityUtils.toString(httpEntry, "UTF-8");
			}
		} catch (IOException e) {
			logger.error("HttpUtil.getRequest found IOException.", e);
		} finally {
			try {
				if(null != httpResponse) {
					httpResponse.close();
				}
				if(null != hc) {
					hc.close();
				}
			} catch (IOException e) {
				logger.error("HttpUtil.getRequest found IOException.", e);
			}
		}

		return result;
	}

	/**
	 * Batch write cookie
	 *
	 * @param cookieMap
	 * @param response
	 */
	public static void writeCookie(Map<String, Object> cookieMap,HttpServletResponse response) {
		for (Map.Entry<String, Object> element : cookieMap.entrySet()) {
			writeCookie((String) element.getKey(),StringUtil.getObjectStringValue(element.getValue()),response);
		}
	}

	/**
	 * write cookie
	 * cookie.setMaxAge(maxAge) ，此方法接收一个以秒为单位的整数。
	 *  （1）maxAge为正整数表示cookie的最大生存期；
	 *  （2）maxAge为负值表示当关闭浏览器时，该cookie将被清除；
	 *  （3）maxAge为零表示必须立即清除该cookie；
	 * @param name
	 * @param value
	 * @param response
	 */
	public static void writeCookie(String name, String value,HttpServletResponse response) {

		String[] maxAgeCols1 = new String[] { "cps", "cps_type", "channelId","u_id", "channel_name", "channel_type" };
		int maxAge = Arrays.asList(maxAgeCols1).contains(name) ? 30 * 24 * 60 * 60 : -1;

		String[] maxAgeCols2 = new String[] { "loginType" };
		maxAge = Arrays.asList(maxAgeCols2).contains(name) ? 10 : -1;

		String[] valueCols2 = new String[] { "xiu.login.userName" };
		String tValue = Arrays.asList(valueCols2).contains(name) ? encode(value, UTF8).replaceAll("\\+", "%20") : encode(value, UTF8);

		Cookie cookie = new Cookie(name, tValue);
		cookie.setMaxAge(maxAge);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setDomain(".xiu.com");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		response.addCookie(cookie);
	}

	/**
	 * read cookie by cookie's name
	 *
	 * @param req
	 * @param cookieName
	 * @return
	 */
	public static String readCookie(HttpServletRequest req, String cookieName) {
		String value = "";
		if (req != null) {
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie cookie : req.getCookies()) {
					if (cookie.getName().equals(cookieName)) {
						value = decode(cookie.getValue(), UTF8);
					}
				}
			}
		}
		return value;
	}

	/**
	 * remove cookies
	 *
	 * @param req
	 * @param res
	 */
	public static void removeCookie(HttpServletRequest req,HttpServletResponse res) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().startsWith("ZS_USER")) {
				cookie.setMaxAge(0);
				cookie.setSecure(false);
				cookie.setPath("/");
				cookie.setDomain(".xiu.com");
				res.addCookie(cookie);
			}
		}
	}

	public static String readCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		String value = null;
		String userInfo = null;
		if (cookies != null && cookies.length != 0) {
			String userInfoKey = "users";
			javax.servlet.http.Cookie c = null;
			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				if (c.getName().equalsIgnoreCase(userInfoKey)) {
					userInfo = c.getValue();
					break;
				} else if (c.getName().equalsIgnoreCase(key)) {
					value = c.getValue();
					break;
				}
			}
			// 从已合并的coookie信息中查找需要的内容
			if (StringUtil.isNullString(value) && StringUtil.isNotEmpty(userInfo)) {
				String startStr = key + "=";
				int start = userInfo.indexOf(startStr);
				if (start != -1) {
					int end = userInfo.indexOf("|", start);
					System.out.println("start:" + start + "; end:" + end);
					if (end == -1) {
						value = userInfo.substring(start + startStr.length());
					} else {
						value = userInfo.substring(start + startStr.length(),end);
					}
				}
			}
		}
		return value;
	}
	
	/**
	 * @Description: 将字符串 s 进行 enc编码
	 * @param s  待编码字符串内容 比如：a
	 * @param enc 待编码类型 比如：UTF-8
	 * @return
	 */
	public static String encode(String s, String enc) {
		String value = "";
		try {
			if (StringUtil.isNotEmpty(s) && StringUtil.isNotEmpty(enc)) {
				value = java.net.URLEncoder.encode(s, enc);
			}
		} catch (UnsupportedEncodingException e) {
			return value;
		}
		return value;
	}
	
	/**
	 * @Description: 将字符串 s 进行 enc解码
	 * @param s  待解码字符串内容 比如：a
	 * @param enc 待解码类型 比如：UTF-8
	 * @return
	 */
	public static String decode(String s, String enc) {
		String value = "";
		try {
			if (StringUtil.isNotEmpty(s) && StringUtil.isNotEmpty(enc)) {
				value = java.net.URLDecoder.decode(s, enc);
			}
		} catch (UnsupportedEncodingException e) {
			return value;
		}
		return value;
	}
	
}
