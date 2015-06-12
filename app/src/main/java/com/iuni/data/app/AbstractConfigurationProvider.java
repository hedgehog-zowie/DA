package com.iuni.data.app;

import com.google.api.client.util.Maps;
import com.iuni.data.*;
import com.iuni.data.analyze.DefaultAnalyzeFactory;
import com.iuni.data.conf.AnalyzeConfig;
import com.iuni.data.conf.AnalyzeConfig.AgentConfiguration;
import com.iuni.data.conf.BasicConfigurationConstants;
import com.iuni.data.conf.ComponentConfiguration;
import com.iuni.data.conf.Configurables;
import com.iuni.data.conf.analyze.AnalyzeConfiguration;
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

    // agent name, you can config multiple agent
    private String agentName;

    private final AnalyzeFactory analyzeFactory;

    public AbstractConfigurationProvider(String agentName) {
        super();
        this.agentName = agentName;
        this.analyzeFactory = new DefaultAnalyzeFactory();
    }

    protected abstract AnalyzeConfig getAnalyzeConfig();

    @Override
    public MaterializedConfiguration getConfiguration() {
        MaterializedConfiguration conf = new SimpleMaterializedConfiguration();
        AnalyzeConfig analyzeConfig =  getAnalyzeConfig();
        AgentConfiguration agentConfiguration = analyzeConfig.getConfigurationFor(getAgentName());
        if (agentConfiguration != null) {
            Map<String, Analyze> analyzeMap = Maps.newHashMap();
            try {
                loadAnalyze(agentConfiguration, analyzeMap);
                for (Map.Entry<String, Analyze> entry : analyzeMap.entrySet())
                    conf.addAnalyze(entry.getKey(), entry.getValue());
            } catch (InstantiationException e) {
                logger.error("Failed to instantiate component {}", e);
            } finally {
                analyzeMap.clear();
            }
        } else {
            logger.warn("No configuration found for this host:{}", getAgentName());
        }
        return conf;
    }

    private void loadAnalyze(AgentConfiguration agentConfiguration, Map<String, Analyze> analyzeMap) throws InstantiationException {
        logger.info("Creating analyzes");
        Set<String> analyzeNames = agentConfiguration.getAnalyzeSet();

        // have ComponentConfiguration object
        Map<String, ComponentConfiguration> compMap = agentConfiguration.getAnalyzeConfigMap();
        for (String analyzeName : analyzeNames) {
            ComponentConfiguration comp = compMap.get(analyzeName);
            if (comp != null) {
                AnalyzeConfiguration configuration = (AnalyzeConfiguration) comp;
                Analyze analyze = analyzeFactory.create(comp.getComponentName(), comp.getType());
                try {
                    Configurables.configure(analyze, configuration);
                    analyzeMap.put(comp.getComponentName(), analyze);
                } catch (Exception e) {
                    String msg = String.format("Analyze %s has been removed due to an error during configuration", analyzeName);
                    logger.error(msg, e);
                }
            }
        }

        // don't have ComponentConfiguration object, use Context
        Map<String, Context> analyzeContexts = agentConfiguration.getAnalyzeContextMap();
        for (String analyzeName : analyzeNames) {
            Context context = analyzeContexts.get(analyzeName);
            if (context != null) {
                Analyze analyze = analyzeFactory.create(analyzeName, context.getString(BasicConfigurationConstants.CONFIG_TYPE));
                try {
                    Configurables.configure(analyze, context);
                    analyzeMap.put(analyzeName, analyze);
                } catch (Exception e) {
                    String msg = String.format("Analyze %s has been removed due to an error during configuration", analyzeName);
                    logger.error(msg, e);
                }
            }
        }
    }

    public String getAgentName() {
        return agentName;
    }

}
