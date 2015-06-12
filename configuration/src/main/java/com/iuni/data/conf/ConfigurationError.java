package com.iuni.data.conf;

/**
 * 配置错误
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ConfigurationError {
    private String componentName;
    private String key;
    private final ConfigurationErrorType errorType;
    private ErrorOrWarning error;

    /**
     * @param component
     * @param key
     * @param error
     */
    public ConfigurationError(String component, String key, ConfigurationErrorType error, ErrorOrWarning err) {
        this.error = err;
        if (component != null) {
            this.componentName = component;
        } else {
            this.componentName = "";
        }
        if (key != null) {
            this.key = key;
        } else {
            this.key = "";
        }
        this.errorType = error;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getKey() {
        return key;
    }

    public ConfigurationErrorType getErrorType() {
        return errorType;
    }

    public ErrorOrWarning getErrorOrWarning() {
        return error;
    }

    public enum ErrorOrWarning {
        ERROR,
        WARNING;
    }
}
