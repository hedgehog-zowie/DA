package com.iuni.data.conf;

/**
 * 配置异常类
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ConfigurationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
