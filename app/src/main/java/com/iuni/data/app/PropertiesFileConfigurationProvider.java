package com.iuni.data.app;

import com.google.common.collect.Maps;
import com.iuni.data.conf.AnalyzeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * parse config file
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PropertiesFileConfigurationProvider extends AbstractConfigurationProvider {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesFileConfigurationProvider.class);

    private final File file;

    public PropertiesFileConfigurationProvider(String agentName, File file) {
        super(agentName);
        this.file = file;
    }

    @Override
    public AnalyzeConfig getAnalyzeConfig() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            Properties properties = new Properties();
            properties.load(reader);
            return new AnalyzeConfig(toMap(properties));
        } catch (IOException ex) {
            logger.error("Unable to load file:{} (I/O failure) - Exception follows. {}", file, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.warn("Unable to close file reader for file: {}. {}", file, ex);
                }
            }
        }
        return new AnalyzeConfig(new HashMap<String, String>());
    }

    /**
     * trans properties to map
     * @param properties
     * @return
     */
    private Map<String, String> toMap(Properties properties) {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String value = properties.getProperty(name);
            result.put(name, value);
        }
        return result;
    }

}
