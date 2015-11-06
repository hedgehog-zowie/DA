package com.iuni.data.conf.analyze;

import com.iuni.data.Context;
import com.iuni.data.conf.ComponentConfiguration;
import com.iuni.data.conf.ConfigurationException;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class AnalyzeConfiguration extends ComponentConfiguration {

    public AnalyzeConfiguration(String componentName) {
        super(componentName);
    }

    @Override
    public void configure(Context context) throws ConfigurationException {
        super.configure(context);
    }

    public enum AnalyzeConfigurationType {
        OTHER(null),

        IMPALA("com.iuni.data.conf.analyze.ImpalaConfiguration"),
        HIVE("com.iuni.data.conf.analyze.HiveConfiguration"),
        ACTIVE("com.iuni.data.conf.analyze.ActiveConfiguration"),
        PAGE("com.iuni.data.conf.analyze.PageAnalyzeConfiguration"),
        CLICK("com.iuni.data.conf.analyze.ClickAnalyzeConfiguration"),
        PAGE_FOR_WHOLE_SITE("com.iuni.data.conf.analyze.PageAnalyzeForWholeSiteConfiguration"),
        WHOLE_SITE_BY_CHANNEL("com.iuni.data.conf.analyze.WholeSiteAnalyzeByChannelConfiguration"),
        ;

        private String analyzeConfigurationName;

        private AnalyzeConfigurationType(String configurationName) {
            this.analyzeConfigurationName = configurationName;
        }

        public AnalyzeConfiguration getConfiguration(String name) throws ConfigurationException {
            if (this.equals(OTHER)) {
                return new AnalyzeConfiguration(name);
            }
            Class<? extends AnalyzeConfiguration> clazz = null;
            AnalyzeConfiguration instance = null;
            try {
                if(analyzeConfigurationName != null){
                    clazz = (Class<? extends AnalyzeConfiguration>) Class.forName(analyzeConfigurationName);
                    instance = clazz.getConstructor(String.class).newInstance(name);
                }
                else {
                    instance = new AnalyzeConfiguration(name);
                    instance.setNotFoundConfigClass();
                }
            } catch (ClassNotFoundException e) {
                instance = new AnalyzeConfiguration(name);
                instance.setNotFoundConfigClass();
            } catch (Exception e) {
                throw new ConfigurationException("Error creating configuration", e);
            }
            return instance;
        }
    }

}
