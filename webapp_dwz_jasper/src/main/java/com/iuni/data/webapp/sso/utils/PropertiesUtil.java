package com.iuni.data.webapp.sso.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties Util
 * @author CaiKe
 * @version dp-persist-1.0.0
 */
public class PropertiesUtil {

	private static String fileName = "/da-config.properties";
	private static Properties properties;

	/**
	 * 根据KEY获取配置文件的值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		if (properties == null) {
			loadProperties(fileName);
		}
		return properties.getProperty(key);
	}

	/**
	 * 获取该路径的 Properties
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	private static void loadProperties(String filePath) {
		properties = new Properties();
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getResourceAsStream(filePath);
			if (is == null)
				is = new FileInputStream(filePath);
			properties.load(is);
		} catch (Exception e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {

				}
			}
		}
	}
}