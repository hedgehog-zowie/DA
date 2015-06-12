package com.iuni.data.conf;

import com.iuni.data.Context;
import com.iuni.data.conf.ConfigurationError.ErrorOrWarning;
import com.iuni.data.conf.analyze.AnalyzeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class AnalyzeConfig {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzeConfig.class);

    private final Map<String, AgentConfiguration> agentConfigMap;
    private final LinkedList<ConfigurationError> errors;
    public static final String NEWLINE = System.getProperty("line.separator", "\n");

    public AnalyzeConfig(Map<String, String> properties) {
        agentConfigMap = new HashMap<String, AgentConfiguration>();
        errors = new LinkedList<ConfigurationError>();
        for (String name : properties.keySet()) {
            String value = properties.get(name);

            if (!addRawProperty(name, value)) {
                logger.warn("Configuration property ignored: {} = {}", name, value);
            }
        }

        // validate and remove improperly configured components
        validateConfiguration();
    }

    private void validateConfiguration() {
        Iterator<String> it = agentConfigMap.keySet().iterator();

        while (it.hasNext()) {
            String agentName = it.next();
            AgentConfiguration aconf = agentConfigMap.get(agentName);

            if (!aconf.isValid()) {
                logger.warn("Agent configuration invalid for agent ' {}'. It will be removed.", agentName);
                errors.add(new ConfigurationError(agentName, "", ConfigurationErrorType.AGENT_CONFIGURATION_INVALID, ErrorOrWarning.ERROR));
                it.remove();
            }
            logger.debug("Analyze: {} \n", aconf.analyzes);
        }

        logger.info("Post-validation analyze configuration contains configuration for agents: {}", agentConfigMap.keySet());
    }

    private boolean addRawProperty(String name, String value) {
        // Null names and values not supported
        if (name == null || value == null) {
            errors.add(new ConfigurationError("", "", ConfigurationErrorType.AGENT_NAME_MISSING, ErrorOrWarning.ERROR));
            return false;
        }
        // Empty values are not supported
        if (value.trim().length() == 0) {
            errors.add(new ConfigurationError(name, "", ConfigurationErrorType.PROPERTY_VALUE_NULL, ErrorOrWarning.ERROR));
            return false;
        }
        // Remove leading and trailing spaces
        name = name.trim();
        value = value.trim();

        int index = name.indexOf('.');
        // All configuration keys must have a prefix defined as agent name
        if (index == -1) {
            errors.add(new ConfigurationError(name, "", ConfigurationErrorType.AGENT_NAME_MISSING, ErrorOrWarning.ERROR));
            return false;
        }

        String agentName = name.substring(0, index);
        // Agent name must be specified for all properties
        if (agentName.length() == 0) {
            errors.add(new ConfigurationError(name, "", ConfigurationErrorType.AGENT_NAME_MISSING, ErrorOrWarning.ERROR));
            return false;
        }

        String configKey = name.substring(index + 1);
        // Configuration key must be specified for every property
        if (configKey.length() == 0) {
            errors.add(new ConfigurationError(name, "", ConfigurationErrorType.PROPERTY_NAME_NULL, ErrorOrWarning.ERROR));
            return false;
        }

        AgentConfiguration aconf = agentConfigMap.get(agentName);
        if (aconf == null) {
            aconf = new AgentConfiguration(agentName, errors);
            agentConfigMap.put(agentName, aconf);
        }

        // Each configuration key must begin with one of the three prefixes:
        // server, client, or analyze.
        return aconf.addProperty(configKey, value);
    }

    public static class AgentConfiguration {

        private final String agentName;
        private String analyzes;
        private final Map<String, ComponentConfiguration> analyzeConfigMap;
        private Map<String, Context> analyzeContextMap;
        private Set<String> analyzeSet;
        private final List<ConfigurationError> errorList;

        private AgentConfiguration(String agentName, List<ConfigurationError> errorList) {
            this.agentName = agentName;
            this.errorList = errorList;
            this.analyzeConfigMap = new HashMap<String, ComponentConfiguration>();
            this.analyzeContextMap = new HashMap<String, Context>();
        }

        public Map<String, ComponentConfiguration> getAnalyzeConfigMap() {
            return analyzeConfigMap;
        }

        public Map<String, Context> getAnalyzeContextMap() {
            return analyzeContextMap;
        }

        public Set<String> getAnalyzeSet() {
            return analyzeSet;
        }

        public void setAnalyzeSet(Set<String> analyzeSet) {
            this.analyzeSet = analyzeSet;
        }

        public boolean addProperty(String key, String value) {
            // check for analyzes
            if (key.equals(BasicConfigurationConstants.CONFIG_ANALYZES)) {
                if (analyzes == null) {
                    analyzes = value;
                    return true;
                } else {
                    logger.warn("Duplicate analyze list specified for agent: " + agentName);
                    errorList.add(new ConfigurationError(agentName, BasicConfigurationConstants.CONFIG_ANALYZES, ConfigurationErrorType.DUPLICATE_PROPERTY, ErrorOrWarning.ERROR));
                    return false;
                }
            }

            ComponentNameAndConfigKey cnck = parseConfigKey(key, BasicConfigurationConstants.CONFIG_ANALYZES_PREFIX);
            if (cnck != null) {
                // it is an analyze
                String name = cnck.getComponentName();
                Context conf = analyzeContextMap.get(name);

                if (conf == null) {
                    conf = new Context();
                    analyzeContextMap.put(name, conf);
                }

                conf.put(cnck.getConfigKey(), value);
                return true;
            }

            logger.warn("Invalid property specified: " + key);
            errorList.add(new ConfigurationError(agentName, key, ConfigurationErrorType.INVALID_PROPERTY, ErrorOrWarning.ERROR));
            return false;
        }

        private ComponentNameAndConfigKey parseConfigKey(String key, String prefix) {
            // key must start with prefix
            if (!key.startsWith(prefix)) {
                return null;
            }

            // key must have a component name part after the prefix of the format:
            // <prefix><component-name>.<config-key>
            int index = key.indexOf('.', prefix.length() + 1);

            if (index == -1) {
                return null;
            }

            String name = key.substring(prefix.length(), index);
            String configKey = key.substring(prefix.length() + name.length() + 1);

            // name and config key must be non-empty
            if (name.length() == 0 || configKey.length() == 0) {
                return null;
            }

            return new ComponentNameAndConfigKey(name, configKey);
        }

        public boolean isValid() {
            logger.debug("Starting validation of configuration for agent: {}, initial-configuration: {}", agentName, this.getPrevalidationConfig());
            // Make sure that at least one analyze is specified
            if (analyzes == null || analyzes.trim().length() == 0) {
                logger.warn("Agent configuration for '{}' does not contain any analyzes. Marking it as invalid.", agentName);
                errorList.add(new ConfigurationError(agentName,
                        BasicConfigurationConstants.CONFIG_ANALYZES,
                        ConfigurationErrorType.PROPERTY_VALUE_NULL,
                        ErrorOrWarning.ERROR));
                return false;
            }

            analyzeSet = new HashSet<String>(Arrays.asList(analyzes.split("\\s+")));
            analyzeSet = validateAnalyzes(analyzeSet);
            if (analyzeSet.size() == 0) {
                logger.warn("Agent configuration for '{}' does not contain any valid channels. Marking it as invalid.", agentName);
                errorList.add(new ConfigurationError(agentName,
                        BasicConfigurationConstants.CONFIG_ANALYZES,
                        ConfigurationErrorType.PROPERTY_VALUE_NULL,
                        ErrorOrWarning.ERROR));
                return false;
            }

            this.analyzes = getSpaceDelimitedList(analyzeSet);

            logger.debug("Post validation configuration for {} {} {}", agentName, NEWLINE, this.getPostvalidationConfig());

            return true;
        }

        private String getSpaceDelimitedList(Set<String> analyzeSet) {
            if (analyzeSet.size() == 0) {
                return null;
            }

            StringBuilder sb = new StringBuilder("");

            for (String analyze : analyzeSet) {
                sb.append(" ").append(analyze);
            }

            return sb.toString().trim();
        }

        private Set<String> validateAnalyzes(Set<String> analyzeSet) {
            Iterator<String> iterator = analyzeSet.iterator();
            Map<String, Context> newContextMap = new HashMap<String, Context>();
            com.iuni.data.conf.analyze.AnalyzeConfiguration conf = null;
            while (iterator.hasNext()) {
                String analyzeName = iterator.next();
                Context analyzeContext = analyzeContextMap.get(analyzeName);
                if (analyzeContext != null) {
                    AnalyzeType analyzeType = getKnownType(analyzeContext.getString(BasicConfigurationConstants.CONFIG_TYPE));
                    boolean configSpecified = false;
                    String config = null;
                    // Not a known analyze - cannot do specific validation to this analyze
                    if (analyzeType == null) {
                        config = analyzeContext.getString(BasicConfigurationConstants.CONFIG_CONFIG);
                        if (config == null || config.isEmpty()) {
                            config = "OTHER";
                        } else {
                            configSpecified = true;
                        }
                    } else {
                        config = analyzeType.toString().toUpperCase();
                        configSpecified = true;
                    }
                    try {
                        conf = (com.iuni.data.conf.analyze.AnalyzeConfiguration) ComponentConfigurationFactory.create(analyzeName, config, ComponentType.ANALYZE);
                        logger.debug("Created analyze " + analyzeName);
                        if (conf != null) {
                            conf.configure(analyzeContext);
                        }
                        if ((configSpecified && conf.isNotFoundConfigClass()) || !configSpecified) {
                            newContextMap.put(analyzeName, analyzeContext);
                        } else if (configSpecified) {
                            analyzeConfigMap.put(analyzeName, conf);
                        }
                        if (conf != null)
                            errorList.addAll(conf.getErrors());
                    } catch (ConfigurationException e) {
                        // Could not configure analyze - skip it.
                        // No need to add to error list - already added before exception is
                        // thrown
                        if (conf != null) errorList.addAll(conf.getErrors());
                        iterator.remove();
                        logger.warn("Could not configure channel {} due to: {}, {}", analyzeName, e.getMessage(), e);
                    }
                } else {
                    iterator.remove();
                    errorList.add(new ConfigurationError(agentName, analyzeName, ConfigurationErrorType.CONFIG_ERROR, ErrorOrWarning.ERROR));
                }
            }
            analyzeContextMap = newContextMap;
            Set<String> tempSet = new HashSet<String>();
            tempSet.addAll(analyzeConfigMap.keySet());
            tempSet.addAll(analyzeContextMap.keySet());
            analyzeSet.retainAll(tempSet);
            return analyzeSet;
        }

        private AnalyzeType getKnownType(String type) {
            AnalyzeType[] values = AnalyzeType.values();
            for (AnalyzeType value : values) {
                if (value.toString().equalsIgnoreCase(type))
                    return value;
                String analyze = value.getClassName();
                if (analyze != null && analyze.equalsIgnoreCase(type))
                    return value;
            }
            return null;
        }

        public String getPrevalidationConfig() {
            StringBuilder sb = new StringBuilder("AgentConfiguration[");
            sb.append(agentName).append("]").append(NEWLINE).append("analyzes: ");
            sb.append(analyzeContextMap).append(NEWLINE);
            return sb.toString();
        }

        public String getPostvalidationConfig() {
            StringBuilder sb = new StringBuilder("AgentConfiguration created without Configuration stubs for which only basic syntactical validation was performed[")
                    .append(agentName).append("]").append(NEWLINE);
            if (!analyzeContextMap.isEmpty()) {
                sb.append("analyzes: ").append(analyzeContextMap).append(NEWLINE);
            }
            if (!analyzeConfigMap.isEmpty()) {
                sb.append("AgentConfiguration created with Configuration stubs for which full validation was performed[")
                        .append(agentName).append("]").append(NEWLINE).append("analyzes: ").append(analyzeConfigMap).append(NEWLINE);
            }
            return sb.toString();
        }
    }

    public static class ComponentNameAndConfigKey {

        private final String componentName;
        private final String configKey;

        private ComponentNameAndConfigKey(String name, String configKey) {
            this.componentName = name;
            this.configKey = configKey;
        }

        public String getComponentName() {
            return componentName;
        }

        public String getConfigKey() {
            return configKey;
        }
    }

    public LinkedList<ConfigurationError> getConfigurationErrors() {
        return errors;
    }

    public AgentConfiguration getConfigurationFor(String hostname) {
        return agentConfigMap.get(hostname);
    }
}

