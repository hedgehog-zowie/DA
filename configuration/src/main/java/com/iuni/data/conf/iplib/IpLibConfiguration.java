package com.iuni.data.conf.iplib;

import com.iuni.data.Context;
import com.iuni.data.conf.ComponentConfiguration;
import com.iuni.data.conf.ConfigurationException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class IpLibConfiguration extends ComponentConfiguration {

    public IpLibConfiguration(String componentName) {
        super(componentName);
    }

    @Override
    public void configure(Context context) throws ConfigurationException {
        super.configure(context);
    }

    public enum IpLibConfigurationType {
        OTHER(null),

        IUNI("com.iuni.data.conf.iplib.IuniIpLibConfiguration"),
        TAOBAO("com.iuni.data.conf.iplib.TaobaoIpLibConfiguration"),
        PURE("com.iuni.data.conf.iplib.PureIpLibConfiguration"),
        GEO("com.iuni.data.conf.iplib.GeoIpLibConfiguration"),
        ;

        private String iplibConfigurationName;

        private IpLibConfigurationType(String configurationName) {
            this.iplibConfigurationName = configurationName;
        }

        public String getIplibConfigurationName() {
            return this.iplibConfigurationName;
        }

        public IpLibConfiguration getConfiguration(String name) throws ConfigurationException {
            if (this.equals(IpLibConfigurationType.OTHER)) {
                return new IpLibConfiguration(name);
            }
            Class<? extends IpLibConfiguration> clazz = null;
            IpLibConfiguration instance = null;
            try {
                if (iplibConfigurationName != null) {
                    clazz = (Class<? extends IpLibConfiguration>) Class.forName(iplibConfigurationName);
                    instance = clazz.getConstructor(String.class).newInstance(name);
                } else {
                    instance = new IpLibConfiguration(name);
                    instance.setNotFoundConfigClass();
                }
            } catch (ClassNotFoundException e) {
                instance = new IpLibConfiguration(name);
                instance.setNotFoundConfigClass();
            } catch (Exception e) {
                throw new ConfigurationException("Error creating configuration", e);
            }
            return instance;
        }
    }

}
