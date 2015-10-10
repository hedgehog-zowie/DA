package com.iuni.data.webapp.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DataFormatUtils {
	
	/**
	 * double型 保留两位小数
	 * */
	public static String doulleFormat(int a ,int b){
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format((double)a/b);
	}
	
	/**
	 * 百分比保留两位小数 a/b
	 */
	public static String percentFormat(int a,int b){
		NumberFormat nt = NumberFormat.getPercentInstance();
		nt.setMinimumFractionDigits(2);
		return nt.format((double)a/b);
	}
}
