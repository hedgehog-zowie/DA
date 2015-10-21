package com.iuni.data.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;

/**
 * 基于Java的进制转换器
 * @author CaiKe
 * @version gionee-common-1.0.0
 */
public class NumBaseConvertor {

	private static final String allBaseChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ%&";
	
	private static final BigInteger zeroBase10 = new BigInteger("0", 10);
	
	/**
	 * 10机制转X进制
	 * @param value
	 * @return String
	 */
	public static String base10ToX(String value, int baseNum) {
		String result = "";
		
		if(StringUtils.isBlank(value)) {
			return result;
		}
		
		String baseChar = allBaseChar.substring(0, baseNum);
		BigInteger val = new BigInteger(value, 10);
		BigInteger base = new BigInteger(String.valueOf(baseNum), 10);
		while(val.compareTo(zeroBase10) == 1) {
			BigInteger temp = val.remainder(base);
			result = baseChar.charAt(temp.intValue()) + result;
			val = val.subtract(temp).divide(base);
		}
		
		return result;
	}
	
	/**
	 * X机制转10进制
	 * @param value
	 * @param baseNum
	 * @return String
	 */
	public static String xToBase10(String value, int baseNum) {
		
		if(StringUtils.isBlank(value)) {
			return "";
		}
		
		String baseChar = allBaseChar.substring(0, baseNum);
		BigInteger base = new BigInteger(String.valueOf(baseNum), 10);
		
		BigInteger val = new BigInteger("0", 10);
		BigInteger temp = new BigInteger("1", 10);
		
		for(int len = value.length()-1; len > -1; len--)
		{
			val = val.add(temp.multiply(new BigInteger(String.valueOf(baseChar.indexOf(value.charAt(len))))));
			temp = temp.multiply(base);
		}
		return val.toString(10);
	}
	
	/**
	 * X机制转Y进制
	 * @param value
	 * @param oldBaseNum
	 * @param newBaseNum
	 * @return String
	 */
	public static String xToBaseY(String value, int oldBaseNum, int newBaseNum) {
		String result = "";
		
		if(StringUtils.isBlank(value)) {
			return result;
		}
		
		result = xToBase10(value, oldBaseNum);
		if(StringUtils.isBlank(result)) {
			return result;
		}
		
		result = base10ToX(result, newBaseNum);
		return result;
	}
	
}
