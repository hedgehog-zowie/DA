package com.iuni.data.conf;

import com.iuni.data.Context;
import com.iuni.data.conf.ConfigurationError.ErrorOrWarning;

import java.util.LinkedList;
import java.util.List;

/**
 * component config base class
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class ComponentConfiguration {

    protected String componentName;
    // component type
    private String type;
    // component has been configured, if configured, it's true, if not, it's false
    protected boolean configured;
    // list all configuration error of this component
    protected List<ConfigurationError> errors;
    // if not found config class, it's true, otherwise it's false
    private boolean notFoundConfigClass;
    // too blank chars to indent string
    private static final String INDENTSTEP = "  ";

    public boolean isNotFoundConfigClass() {
        return notFoundConfigClass;
    }

    public void setNotFoundConfigClass() {
        this.notFoundConfigClass = true;
    }

    protected ComponentConfiguration(String componentName) {
        this.componentName = componentName;
        errors = new LinkedList<ConfigurationError>();
        this.type = null;
        configured = false;
    }

    public List<ConfigurationError> getErrors() {
        return errors;
    }

    public void configure(Context context) throws ConfigurationException {
        failIfConfigured();
        String confType = context.getString(BasicConfigurationConstants.CONFIG_TYPE);
        if (confType != null && !confType.isEmpty()) {
            this.type = confType;
        }

        if (this.type == null || this.type.isEmpty()) {
            errors.add(new ConfigurationError(componentName,
                    BasicConfigurationConstants.CONFIG_TYPE,
                    ConfigurationErrorType.ATTRS_MISSING, ErrorOrWarning.ERROR));

            throw new ConfigurationException("component has no type. Cannot configure. " + componentName);
        }
    }

    protected void failIfConfigured() throws ConfigurationException {
        if (configured) {
            throw new ConfigurationException("Already configured component." + componentName);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int indentCount) {
        StringBuilder indentSb = new StringBuilder("");

        for (int i = 0; i < indentCount; i++) {
            indentSb.append(INDENTSTEP);
        }

        String indent = indentSb.toString();
        StringBuilder sb = new StringBuilder(indent);

        sb.append("ComponentConfiguration[").append(componentName).append("]");
        sb.append(indent).append(INDENTSTEP).append("CONFIG: ");
        sb.append(indent).append(INDENTSTEP);

        return sb.toString();
    }

    public String getComponentName() {
        return componentName;
    }

    protected void setConfigured() {
        configured = true;
    }

    public enum ComponentType {
        OTHER(null),
        IPLIB("IpLib"),
        SERVER("Server"),
        CLIENT("Client"),
        ;

        private final String componentType;
        private ComponentType(String type){
            componentType = type;
        }
        public String getComponentType() {
            return componentType;
        }
    }

}
