package com.iuni.data.conf;

/**
 * 错误类型
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ConfigurationErrorType {
    OTHER(null),
    AGENT_CONFIGURATION_INVALID("Agent configuration is invalid."),
    PROPERTY_NAME_NULL("Property needs a name."),
    PROPERTY_VALUE_NULL("Property value missing."),
    AGENT_NAME_MISSING("Agent name is required."),
    CONFIGURATION_KEY_ERROR("Configuration Key is invalid."),
    DUPLICATE_PROPERTY("Property already configured."),
    INVALID_PROPERTY("No such property."),
    ATTRS_MISSING("Required attributes missing."),
    ILLEGAL_PROPERTY_NAME("This attribute name is invalid."),
    DEFAULT_VALUE_ASSIGNED("Value in configuration is invalid for this key, assigned default value."),
    CONFIG_ERROR("Configuration of component failed.");
    private final String error;

    private ConfigurationErrorType(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
