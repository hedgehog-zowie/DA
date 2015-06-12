package com.iuni.data.conf;

import com.iuni.data.Context;
import com.iuni.data.conf.ConfigurationError.ErrorOrWarning;
import com.iuni.data.conf.iplib.IpLibType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class IpLibConfig {

    private static final Logger logger = LoggerFactory.getLogger(IpLibConfig.class);

    private final Map<String, AgentConfiguration> agentConfigMap;
    private final LinkedList<ConfigurationError> errors;
    public static final String NEWLINE = System.getProperty("line.separator", "\n");
    public static final String INDENTSTEP = "  ";

    public IpLibConfig(Map<String, String> properties) {
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
            logger.debug("IpLibs: {} \n", aconf.ipLibs);
        }

        logger.info("Post-validation ipLib configuration contains configuration for agents: {}", agentConfigMap.keySet());
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
        // server, client, or ipLib.
        return aconf.addProperty(configKey, value);
    }

    public static class AgentConfiguration {

        private final String agentName;
        private String ipLibs;
        private final Map<String, ComponentConfiguration> ipLibConfigMap;
        private Map<String, Context> ipLibContextMap;
        private Set<String> ipLibSet;
        private final List<ConfigurationError> errorList;

        private AgentConfiguration(String agentName, List<ConfigurationError> errorList) {
            this.agentName = agentName;
            this.errorList = errorList;
            this.ipLibConfigMap = new HashMap<String, ComponentConfiguration>();
            this.ipLibContextMap = new HashMap<String, Context>();
        }

        public Map<String, ComponentConfiguration> getIpLibConfigMap() {
            return ipLibConfigMap;
        }

        public Map<String, Context> getIpLibContextMap() {
            return ipLibContextMap;
        }

        public Set<String> getIpLibSet() {
            return ipLibSet;
        }

        public void setIpLibSet(Set<String> ipLibSet) {
            this.ipLibSet = ipLibSet;
        }

        public boolean addProperty(String key, String value) {
            // check for ipLibs
            if (key.equals(BasicConfigurationConstants.CONFIG_IPLIBS)) {
                if (ipLibs == null) {
                    ipLibs = value;
                    return true;
                } else {
                    logger.warn("Duplicate ipLib list specified for agent: " + agentName);
                    errorList.add(new ConfigurationError(agentName, BasicConfigurationConstants.CONFIG_IPLIBS, ConfigurationErrorType.DUPLICATE_PROPERTY, ErrorOrWarning.ERROR));
                    return false;
                }
            }

//            ComponentNameAndConfigKey ipLibsCnck = parseConfigKey(key, BasicConfigurationConstants.CONFIG_IPLIBS_PREFIX);
//            if (ipLibsCnck != null) {
//                // it is a ipLib
//                String name = ipLibsCnck.getComponentName();
//                Context ipLibConf = ipLibContextMap.get(name);
//
//                if (ipLibConf == null) {
//                    ipLibConf = new Context();
//                    ipLibContextMap.put(name, ipLibConf);
//                }
//
//                ipLibConf.put(ipLibsCnck.getConfigKey(), value);
//                return true;
//            }

            ComponentNameAndConfigKey cnck = parseConfigKey(key, BasicConfigurationConstants.CONFIG_IPLIBS_PREFIX);
            if (cnck != null) {
                // it is an ipLib
                String name = cnck.getComponentName();
                Context ipConf = ipLibContextMap.get(name);

                if (ipConf == null) {
                    ipConf = new Context();
                    ipLibContextMap.put(name, ipConf);
                }

                ipConf.put(cnck.getConfigKey(), value);
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
            // Make sure that at least one ipLib is specified
            if (ipLibs == null || ipLibs.trim().length() == 0) {
                logger.warn("Agent configuration for '{}' does not contain any ipLibs. Marking it as invalid.", agentName);
                errorList.add(new ConfigurationError(agentName,
                        BasicConfigurationConstants.CONFIG_IPLIBS,
                        ConfigurationErrorType.PROPERTY_VALUE_NULL,
                        ErrorOrWarning.ERROR));
                return false;
            }

            ipLibSet = new HashSet<String>(Arrays.asList(ipLibs.split("\\s+")));
            ipLibSet = validateIpLibs(ipLibSet);
            if (ipLibSet.size() == 0) {
                logger.warn("Agent configuration for '{}' does not contain any valid ipLibs. Marking it as invalid.", agentName);
                errorList.add(new ConfigurationError(agentName,
                        BasicConfigurationConstants.CONFIG_IPLIBS,
                        ConfigurationErrorType.PROPERTY_VALUE_NULL,
                        ErrorOrWarning.ERROR));
                return false;
            }

            this.ipLibs = getSpaceDelimitedList(ipLibSet);

            logger.debug("Post validation configuration for {} {} {}", agentName, NEWLINE, this.getPostvalidationConfig());

            return true;
        }

        private String getSpaceDelimitedList(Set<String> ipLibSet) {
            if (ipLibSet.size() == 0) {
                return null;
            }

            StringBuilder sb = new StringBuilder("");

            for (String ipLib : ipLibSet) {
                sb.append(" ").append(ipLib);
            }

            return sb.toString().trim();
        }

        private Set<String> validateIpLibs(Set<String> ipLibSet) {
            Iterator<String> iterator = ipLibSet.iterator();
            Map<String, Context> newContextMap = new HashMap<String, Context>();
            com.iuni.data.conf.iplib.IpLibConfiguration conf = null;
            while (iterator.hasNext()) {
                String ipLibName = iterator.next();
                Context ipLibContext = ipLibContextMap.get(ipLibName);
                if (ipLibContext != null) {
                    IpLibType iplibType = getKnownIpLib(ipLibContext.getString(BasicConfigurationConstants.CONFIG_TYPE));
                    boolean configSpecified = false;
                    String config = null;
                    // Not a known ipLib - cannot do specific validation to this ipLib
                    if (iplibType == null) {
                        config = ipLibContext.getString(BasicConfigurationConstants.CONFIG_CONFIG);
                        if (config == null || config.isEmpty()) {
                            config = "OTHER";
                        } else {
                            configSpecified = true;
                        }
                    } else {
                        config = iplibType.toString().toUpperCase();
                        configSpecified = true;
                    }
                    try {
                        conf = (com.iuni.data.conf.iplib.IpLibConfiguration) ComponentConfigurationFactory.create(ipLibName, config, ComponentType.IPLIB);
                        logger.debug("Created ipLib " + ipLibName);
                        if (conf != null) {
                            conf.configure(ipLibContext);
                        }
                        if ((configSpecified && conf.isNotFoundConfigClass()) || !configSpecified) {
                            newContextMap.put(ipLibName, ipLibContext);
                        } else if (configSpecified) {
                            ipLibConfigMap.put(ipLibName, conf);
                        }
                        if (conf != null)
                            errorList.addAll(conf.getErrors());
                    } catch (ConfigurationException e) {
                        // Could not configure ipLib - skip it.
                        // No need to add to error list - already added before exception is
                        // thrown
                        if (conf != null) errorList.addAll(conf.getErrors());
                        iterator.remove();
                        logger.warn("Could not configure channel {} due to: {}, {}", ipLibName, e.getMessage(), e);
                    }
                } else {
                    iterator.remove();
                    errorList.add(new ConfigurationError(agentName, ipLibName, ConfigurationErrorType.CONFIG_ERROR, ErrorOrWarning.ERROR));
                }
            }
            ipLibContextMap = newContextMap;
            Set<String> tempIpLibSet = new HashSet<String>();
            tempIpLibSet.addAll(ipLibConfigMap.keySet());
            tempIpLibSet.addAll(ipLibContextMap.keySet());
            ipLibSet.retainAll(tempIpLibSet);
            return ipLibSet;
        }

        private IpLibType getKnownIpLib(String type) {
            IpLibType[] values = IpLibType.values();
            for (IpLibType value : values) {
                if (value.toString().equalsIgnoreCase(type))
                    return value;
                String ipLib = value.getIpLibClassName();
                if (ipLib != null && ipLib.equalsIgnoreCase(type))
                    return value;
            }
            return null;
        }

        public String getPrevalidationConfig() {
            StringBuilder sb = new StringBuilder("AgentConfiguration[");
            sb.append(agentName).append("]").append(NEWLINE).append("ipLibs: ");
            sb.append(ipLibContextMap).append(NEWLINE);
            return sb.toString();
        }

        public String getPostvalidationConfig() {
            StringBuilder sb = new StringBuilder("AgentConfiguration created without Configuration stubs for which only basic syntactical validation was performed[")
                    .append(agentName).append("]").append(NEWLINE);
            if (!ipLibContextMap.isEmpty()) {
                sb.append("ipLibs: ").append(ipLibContextMap).append(NEWLINE);
            }
            if (!ipLibConfigMap.isEmpty()) {
                sb.append("AgentConfiguration created with Configuration stubs for which full validation was performed[")
                        .append(agentName).append("]").append(NEWLINE).append("ipLibs: ").append(ipLibConfigMap).append(NEWLINE);
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

