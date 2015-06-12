package com.iuni.data.ws.util;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 字符串工具类
 * @author CaiKe
 * @version dp-service-1.0.0
 */
public final class StringUtil {
	
	private StringUtil() {

	}

	public static String getStringValue(final String inStr) {
		return (isNullString(inStr)) ? "" : inStr;
	}

	public static String getObjectStringValue(final Object obj, final String defaultStr) {
		if (obj == null) {
			if (defaultStr != null) {
				return defaultStr;
			} else {
				return "";
			}
		} else {
			return String.valueOf(obj);
		}
	}
	
	public static String getObjectStringValue(final Object obj) {
		if (obj == null) {
				return "";
		} else {
			String value = String.valueOf(obj);
			if(isNullString(value) || "null".equalsIgnoreCase(value)){
				return "";
			}
			return String.valueOf(obj);
		}
	}

	/**
	 * Check if the input string is null.
	 * @param inStr
	 * @return boolean value indicating if the string is null
	 */
	public static boolean isNullString(final String inStr) {
		if (inStr == null || inStr.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Converts a hexadecimal string to a byte array.
	 * @param hexStr Input hexadecimal string.
	 * @return Byte array.
	 * @throws Exception
	 */
	public static byte[] hexToBytes(String hexStr) throws Exception {
		if (hexStr == null) {
			return null;
		}
		if (hexStr.length() % 2 != 0) {
			throw new Exception("Length of data is not equal to even number");
		}
		byte[] rtnBytes = new byte[hexStr.length() / 2];

		for (int i = 0; i < hexStr.length() / 2; i++) {
			rtnBytes[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
		}
		return rtnBytes;
	}

	/**
	 * Converts a byte array to string.
	 * @param data Input byte array.
	 * @return String
	 */
	public static String hexToString(byte[] data) {
		if (data == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xFF & data[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * Parse a string into a series of string tokens using the specified delimiter.
	 * @param str
	 * @param splitChar
	 * @return Array of string token
	 */
	public static String[] split(String str, char splitChar) {
		if (str == null) {
			return null;
		}
		if (str.trim().equals("")) {
			return new String[0];
		}
		if (str.indexOf(splitChar) == -1) {
			String[] strArray = new String[1];
			strArray[0] = str;
			return strArray;
		}

		ArrayList<String> list = new ArrayList<String>();
		int prevPos = 0;
		for (int pos = str.indexOf(splitChar); pos >= 0; pos = str.indexOf(splitChar, (prevPos = (pos + 1)))) {
			list.add(str.substring(prevPos, pos));
		}
		list.add(str.substring(prevPos, str.length()));

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * Parse a string into a series of string tokens using the specified delimiter.
	 * @param str Input string
	 * @param delim The string delimiter.
	 * @return Array of string tokens.
	 */
	public static String[] tokenize(String str, String delim) {
		String[] strs = null;
		if (str != null) {
			StringTokenizer tokens;
			if (delim == null) {
				tokens = new StringTokenizer(str);
			} else {
				tokens = new StringTokenizer(str, delim);
			}
			strs = new String[tokens.countTokens()];
			for (int i = 0; i < strs.length && tokens.hasMoreTokens(); i++) {
				strs[i] = tokens.nextToken();
			}
		}
		return strs;
	}

	/**
	 * Parse a string into a series of string tokens according to fixed length
	 * and return tokenized string array.
	 * @param str Input string
	 * @param fixedLength  The length at which the string is tokenized.
	 * @return Array of string tokens.
	 */
	public static String[] tokenize(String str, int fixedLength) {
		String[] strs = null;
		if (str != null && fixedLength > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < str.length(); i += fixedLength) {
				int next = i + fixedLength;
				if (next > str.length()) next = str.length();
				list.add(str.substring(i, next));
			}
			strs = (String[]) list.toArray(new String[] {});
		}
		return strs;
	}

	/**
	 * Convert the input string to String encoded in UTF16LE format.
	 * @param input
	 * @return String encoded in UTF16LE format
	 * @throws java.io.IOException
	 */
	public static String toUTF16LEString(String input) throws IOException {
		if (input == null || input.length() == 0) {
			return input;
		}
		byte[] b = input.getBytes("UTF-16LE");
		return hexToString(b);
	}

	/**
	 * Left padding the string with the specified padding character upto the specified length.
	 * @param inStr Input string
	 * @param length Padding length
	 * @param paddingChar Padding character
	 * @return Padding string
	 */
	public static String leftPad(String inStr, int length, char paddingChar) {
		if (inStr.length() == length) return inStr;
		StringBuffer outStr = new StringBuffer();
		for (int i = inStr.length(); i < length; i++) {
			outStr.append(paddingChar);
		}
		outStr.append(inStr);
		return outStr.toString();
	}

	/**
	 * Right padding the string with the specified padding character upto the specified length.
	 * @param inStr Input string
	 * @param length Padding length
	 * @param paddingChar Padding character
	 * @return Padding string
	 */
	public static String rightPad(String inStr, int length, char paddingChar) {
		if (inStr.length() == length) return inStr;
		StringBuffer outStr = new StringBuffer();
		outStr.append(inStr);
		for (int i = inStr.length(); i < length; i++) {
			outStr.append(paddingChar);
		}
		return outStr.toString();
	}

	/**
	 * Adds leading zeros to the given String to the specified length. Nothing
	 * will be done if the length of the given String is equal to or greater
	 * than the specified length.
	 * @param s The source string.
	 * @param len The length of the target string.
	 * @return The String after adding leading zeros.
	 */
	public static String addLeadingZero(String s, int len) {
		return addLeadingCharacter(s, '0', len);
	}

	/**
	 * Adds leading spaces to the given String to the specified length. Nothing
	 * will be done if the length of the given String is equal to or greater
	 * than the specified length.
	 * @param s The source string.
	 * @param len The length of the target string.
	 * @return The String after adding leading spaces.
	 */
	public static String addLeadingSpace(String s, int len) {
		return addLeadingCharacter(s, ' ', len);
	}

	/**
	 * Adds specified leading characters to the specified length. Nothing will
	 * be done if the length of the given String is equal to or greater than the
	 * specified length.
	 * @param s The source string.
	 * @param c The leading character(s) to be added.
	 * @param len The length of the target string.
	 * @return The String after adding the specified leading character(s).
	 */
	public static String addLeadingCharacter(String s, char c, int len) {
		if (s != null) {
			StringBuffer sb = new StringBuffer();
			int count = len - s.length();
			for (int i = 0; i < count; i++) {
				sb.append(c);
			}
			sb.append(s);
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * Removes leading zeros from the given String, if any.
	 * @param s The source string.
	 * @return The String after removing leading zeros.
	 */
	public static String removeLeadingZero(String s) {
		return removeLeadingCharacter(s, '0');
	}

	/**
	 * Removes leading spaces from the given String, if any.
	 * 
	 * @param s The source string.
	 * @return The String after removing leading spaces.
	 */
	public static String removeLeadingSpace(String s) {
		return removeLeadingCharacter(s, ' ');
	}

	/**
	 * Removes specified leading characters from the given String, if any.
	 * @param s The source string.
	 * @param c The leading character(s) to be removed.
	 * @return The String after removing the specified leading character(s).
	 */
	public static String removeLeadingCharacter(String s, char c) {
		if (s != null) {
			int len = s.length();
			int i = 0;
			for (i = 0; i < len; i++) {
				if (s.charAt(i) != c) {
					break;
				}
			}
			if (i > 0) {
				return s.substring(i);
			} else {
				return s;
			}
		} else {
			return null;
		}
	}

	/**
	 * Appends zeros to the given String to the specified length. Nothing will
	 * be done if the length of the given String is equal to or greater than the
	 * specified length.
	 * @param s The source string.
	 * @param len The length of the target string.
	 * @return The String after appending zeros.
	 */
	public static String appendZero(String s, int len) {
		return appendCharacter(s, '0', len);
	}

	/**
	 * Appends spaces to the given String to the specified length. Nothing will
	 * be done if the length of the given String is equal to or greater than the
	 * specified length.
	 * @param s The source string.
	 * @param len The length of the target string.
	 * @return The String after appending spaces.
	 */
	public static String appendSpace(String s, int len) {
		return appendCharacter(s, ' ', len);
	}

	/**
	 * Appends specified characters to the given String to the specified length.
	 * Nothing will be done if the length of the given String is equal to or
	 * greater than the specified length.
	 * @param s The source string.
	 * @param c The character(s) to be appended.
	 * @param len The length of the target string.
	 * @return The String after appending the specified character(s).
	 */
	public static String appendCharacter(String s, char c, int len) {
		if (s != null) {
			StringBuffer sb = new StringBuffer().append(s);
			while (sb.length() < len) {
				sb.append(c);
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * Replaces all the occurences of a search string in a given String with a
	 * specified substitution.
	 * @param text The String to be searched.
	 * @param src The search String.
	 * @param tar The replacement String.
	 * @return The result String after replacing.
	 */
	public static String replace(String text, String src, String tar) {
		StringBuffer sb = new StringBuffer();
		if (text == null || src == null || tar == null) {
			return text;
		} else {
			int size = text.length();
			int gap = src.length();

			for (int start = 0; start >= 0 && start < size;) {
				int i = text.indexOf(src, start);
				if (i == -1) {
					sb.append(text.substring(start));
					start = -1;
				} else {
					sb.append(text.substring(start, i)).append(tar);
					start = i + gap;
				}
			}
			return sb.toString();
		}
	}
	
    /**
     * Converting object to String
     * @param obj the converting object
     * @return
     */
	public static String toString(Object obj) {
		if (null == obj) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/**
	 * Calculate the MD5 checksum of the input string
	 * @param inString Input string
	 * @return MD5 checksum of the input string in hexadecimal value
	 */
	public static String md5sum(String inString) {
		MessageDigest algorithm = null;

		try {
			algorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsae) {
			System.err.println("Cannot find digest algorithm");
			return null;
		}

		byte[] defaultBytes = inString.getBytes();
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();
		return hexToString(messageDigest);
	}

	
	public static boolean isNotEmpty(String outstr){ 
		if(outstr!=null&&outstr.trim().length()>0){
			return true;
		}
		return false;
	}
	
	public static String urlEncode(String str) {
		if (null == str) {
			str = "";
		}
		try {
			return URLEncoder.encode(str, CHARSET_GB2312);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	public static String urlDecode(String str) {
		if (null == str) {
			str = "";
		}
		try {
			return URLDecoder.decode(str, CHARSET_GB2312);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}
		
	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
	public static boolean isNullMap(Map<?,?> map){
		Set<?> set=map.keySet();
		Iterator<?> iterable=set.iterator();
		while(iterable.hasNext()){
			Object obj=map.get(iterable.next());
			if(null==obj || obj.equals(null)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static String toJsonStr(Object obj){
		if(obj == null){
			return "";
		}
		return JSONObject.fromObject(obj).toString();
	}
	
	public static Map<String, String> split(String responseBody,
			String splitChar1, String splitChar2) {
		Map<String, String> map = new HashMap<String, String>();
		if (responseBody == null) {
			return map;
		}

		if (responseBody.trim().equals("")) {
			return map;
		}
		if (responseBody.indexOf(splitChar1) == -1 && responseBody.indexOf(splitChar2) != -1) {
			map.put(responseBody.substring(0, responseBody.indexOf(splitChar2)),
					responseBody.substring(responseBody.indexOf(splitChar2) + 1));
		}

		String[] bodyArr = responseBody.split(splitChar1);
		for (String rStr : bodyArr) {
			String[] strArr = rStr.split(splitChar2);
			for (String oStr : strArr) {
				map.put(oStr, strArr.length==1?"":strArr[1]);
			}
		}
		return map;
	}
	
	/**
	 * @Description: 构建类似于 ts=15896565656|xid=995739be567576dd5bf7ac236a636d52 字符串
	 * @param paraMap 基于kev：value的字符串键值对
	 * @param splitChar1 =
	 * @param splitChar2 |
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToString(Map<String, String> paraMap,String splitChar1, String splitChar2) {
		StringBuffer buildMergerCookieSb = new StringBuffer();
		if (paraMap != null && paraMap.size() > 0) {
			int tempSize = paraMap.size() - 1;
			for (Object element : paraMap.entrySet()) {
				Map.Entry entry = (Map.Entry) element; 
				buildMergerCookieSb.append((String)entry.getKey())
				                   .append(splitChar1)
				                   .append((String)entry.getValue());
				if (tempSize > 0) {
					buildMergerCookieSb.append(splitChar2);
				}
			}
		}
		return buildMergerCookieSb.toString();
	}
	
	/**
	 * @Description: 解析类似与 ts=15896565656|xid=995739be567576dd5bf7ac236a636d52 字符串
	 *               根据 =和| 进行分割，分割后放入map中
	 * @param contentStr
	 * @param splitChar1
	 * @param splitChar2
	 * @return
	 */
	public static Map<String, String> StringTOMap(String contentStr,String splitChar1, String splitChar2) {
		Map<String, String> contentMap = new HashMap<String, String>();
		if (isNotEmpty(contentStr)) {
			StringBuffer splitSB = new StringBuffer(2);
			splitSB.append(splitChar1).append(splitChar2);
			StringTokenizer st = new StringTokenizer(contentStr,splitSB.toString());
	        while(st.hasMoreTokens()){
	            contentMap.put(st.nextToken(), st.nextToken());
	        }
		}
		return contentMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static void printfMap(Map<String, String> map) {
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next(); 
			Object key = (Object)entry.getKey(); 
			String value = (String)(HttpUtil.decode((String)entry.getValue(),HttpUtil.UTF8)); 
			//Object value = (Object)((String)entry.getValue());
			System.out.println((String)key+":"+(String)value);
		}
	}
	
	/**
	 * @Description: return trim contStr's left trim char and right trim char after char
	 * @param String contStr
	 * @return String
	 */
	public static String leftAndRightTrimChar(String contStr) {
		return org.apache.commons.lang.StringUtils.trim(contStr);
	}
	
	public static final String equalSeparator = "=";
	public static final String verticalBarSeparator = "|";
    public static final String CHARSET_GB2312 = "GB2312";
	public static final String CHARSET_UTF8 = "UTF-8";
	
	public static void main (String[] args) {
		/*String responseBody = "ts=15896565656|xid=995739be567576dd5bf7ac236a636d52|y=b|";
		Map<String, String> map= StringTOMap(responseBody,equalSeparator,verticalBarSeparator);
		printfMap(map);
		String buildMergerCookieString = mapToString(map,equalSeparator,verticalBarSeparator);
		System.out.println("buildMergerCookieString="+buildMergerCookieString);
		
		Map<String, String> returnInfoMap = new LinkedHashMap<String, String>();
		String showMsg = (String) returnInfoMap.get("ShowMsg");
		String showMsgEncode= HttpUtils.encode(showMsg,HttpUtils.UTF8);
		System.out.println("showMsg="+showMsg+",showMsgEncode="+showMsgEncode);*/
		
		String xuser = "ts=1350892230296|upId=0000000000000000000000000000A4D9|uflag=008|rdate=2012-10-10 15:42:42|cid=100001922|ldate=2012-10-22 13:46:40|xid=cf2aaa974d40805a830bd62177ab7142|showmsg=QQ%E4%BC%9A%E5%91%98%EF%BC%8C%E3%81%90.%26%23039%3BHHH%26nbsp%3B%5C%26nbsp%3B%26nbsp%3B%26nbsp%3Bm|headShow=%3Cb%3EQQ%E5%BD%A9%E8%B4%9D%E5%90%88%E4%BD%9C%E5%95%86%E5%AE%B6%EF%BC%9A%3C%2Fb%3E%E8%89%BA%E9%BE%99-%E7%94%B5%E8%AF%9D%E9%A2%84%E5%AE%9A-QQ%E4%BC%9A%E5%91%98%E8%B4%AD%E7%89%A9%E6%88%90%E5%8A%9F%E6%9C%80%E9%AB%98%E8%BF%94%3Cfont+color%3D%22red%22%3E1%25%3C%2Ffont%3E%E5%BD%A9%E8%B4%9D%E7%A7%AF%E5%88%86%EF%BC%8C%E6%99%AE%E9%80%9A%E7%94%A8%E6%88%B7%E6%9C%80%E9%AB%98%E8%BF%94%3Cfont+color%3D%22red%22%3E1%25%3C%2Ffont%3E%E5%BD%A9%E8%B4%9D%E7%A7%AF%E5%88%86%3Ca+href%3D%220%22+target%3D%22_blank%22%3E%EF%BC%88%E8%AF%A6%E6%83%85%EF%BC%89%3C%2Fa%3E|jifenUrl=http://cb.qq.com/my/my_jifen_source.html|cBPoints=1000|cBBonus=5%25|ac=93c866fcbe13ae7be012dfa9322f2535";
		Map<String, String> xuserMap= StringTOMap(xuser,equalSeparator,verticalBarSeparator);
		printfMap(xuserMap);
		String acContent = xuser.substring(0, xuser.indexOf("ac", 0)-1);
		String ac1 = xuserMap.get("ac");
		System.out.println("acContent="+acContent);
		System.out.println("ac1="+ac1);
		System.out.println(leftAndRightTrimChar("  a  "));
		
		
	}
	
}
