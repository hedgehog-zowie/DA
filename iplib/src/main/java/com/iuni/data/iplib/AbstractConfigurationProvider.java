package com.iuni.data.iplib;

import com.google.api.client.util.Maps;
import com.iuni.data.Context;
import com.iuni.data.IpLib;
import com.iuni.data.IpLibFactory;
import com.iuni.data.conf.BasicConfigurationConstants;
import com.iuni.data.conf.Configurables;
import com.iuni.data.conf.IpLibConfig;
import com.iuni.data.conf.IpLibConfig.AgentConfiguration;
import com.iuni.data.conf.ComponentConfiguration;
import com.iuni.data.conf.iplib.IpLibConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class AbstractConfigurationProvider implements ConfigurationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConfigurationProvider.class);

    // 代理名称，一次可以配置多个代理
    private String agentName;
    // IP库工厂
    private final IpLibFactory ipLibFactory;

    public AbstractConfigurationProvider(String agentName) {
        super();
        this.agentName = agentName;
        this.ipLibFactory = new DefaultIpLibFactory();
    }

    protected abstract IpLibConfig getIpLibConfig();

    @Override
    public MaterializedConfiguration getConfiguration() {
        MaterializedConfiguration conf = new SimpleMaterializedConfiguration();
        IpLibConfig ipLibConfig = getIpLibConfig();
        AgentConfiguration agentConfiguration = ipLibConfig.getConfigurationFor(getAgentName());
        if (agentConfiguration != null) {
            Map<String, IpLib> ipLibMap = Maps.newHashMap();
            try {
                loadIpLib(agentConfiguration, ipLibMap);
                for (Map.Entry<String, IpLib> entry : ipLibMap.entrySet()) {
                    logger.info("add ipLib {}", entry.getKey());
                    conf.addIpLib(entry.getKey(), entry.getValue());
                }
            } catch (InstantiationException e) {
                logger.error("Failed to instantiate component {}", e);
            } finally {
                ipLibMap.clear();
            }
        } else {
            logger.warn("No configuration found for this host:{}", getAgentName());
        }
        return conf;
    }

    private void loadIpLib(AgentConfiguration agentConfiguration, Map<String, IpLib> ipLibMap) throws InstantiationException {
        logger.info("Creating ipLibs");
        Set<String> ipLibNames = agentConfiguration.getIpLibSet();

        // have ComponentConfiguration object
        Map<String, ComponentConfiguration> compMap = agentConfiguration.getIpLibConfigMap();
        for (String ipLibName : ipLibNames) {
            ComponentConfiguration comp = compMap.get(ipLibName);
            if (comp != null) {
                IpLibConfiguration configuration = (IpLibConfiguration) comp;
                IpLib ipLib = ipLibFactory.create(comp.getComponentName(), comp.getType());
                try {
                    Configurables.configure(ipLib, configuration);
                    ipLibMap.put(comp.getComponentName(), ipLib);
                } catch (Exception e) {
                    String msg = String.format("IpLib %s has been removed due to an error during configuration", ipLibName);
                    logger.error(msg, e);
                }
            }
        }

        // don't have ComponentConfiguration object, use Context
        Map<String, Context> ipLibContexts = agentConfiguration.getIpLibContextMap();
        for (String ipLibName : ipLibNames) {
            Context context = ipLibContexts.get(ipLibName);
            if (context != null) {
                IpLib ipLib = ipLibFactory.create(ipLibName, context.getString(BasicConfigurationConstants.CONFIG_TYPE));
                try {
                    Configurables.configure(ipLib, context);
                    ipLibMap.put(ipLibName, ipLib);
                } catch (Exception e) {
                    String msg = String.format("IpLib %s has been removed due to an error during configuration", ipLibName);
                    logger.error(msg, e);
                }
            }
        }
    }

    public String getAgentName() {
        return agentName;
    }

}
