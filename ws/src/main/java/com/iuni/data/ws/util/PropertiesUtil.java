package com.iuni.data.ws.util;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Properties;

/**
 * 参数读取
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PropertiesUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PropertiesUtil.class);

    private static String fileName = "/hbase.properties";
    private static Properties properties;

    public static String get(String key) {
        if (properties == null) {
            loadProperties(fileName);
        }
        return properties.getProperty(key);
    }

    /**
     * 根据KEY获取配置文件的值
     *
     * @param key
     * @return
     */
    public static String get(String key, String defaultValue) {
        if (properties == null) {
            loadProperties(fileName);
        }
        String value = properties.getProperty(key);
        if (StringUtils.isEmpty(value))
            value = defaultValue;
        return value;
    }

    /**
     * 获取该路径的 Properties
     *
     * @param filePath 文件路径
     * @return
     */
    private static void loadProperties(String filePath) {
        properties = new Properties();
        BufferedReader reader = null;
        try {
            logger.info(filePath);
            logger.info(PropertiesUtil.class.getResource(filePath).getPath());
            reader = new BufferedReader(new FileReader(new File(PropertiesUtil.class.getResource(filePath).getPath())));
            properties.load(reader);
        } catch (Exception e) {
            logger.error("Load hbase properties failed, ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.warn("Unable to close file reader for file: {}. {}", filePath, ex);
                }
            }
        }
    }

}
