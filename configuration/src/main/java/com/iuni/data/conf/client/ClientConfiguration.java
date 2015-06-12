package com.iuni.data.conf.client;

import com.iuni.data.Context;
import com.iuni.data.conf.ComponentConfiguration;
import com.iuni.data.conf.ConfigurationException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ClientConfiguration extends ComponentConfiguration {
    public ClientConfiguration(String componentName) {
        super(componentName);
    }

    @Override
    public void configure(Context context) throws ConfigurationException {
        super.configure(context);
    }

    public enum ClientConfigurationType {
        OTHER(null),
        HTTP("com.iuni.data.conf.client.HttpClientConfiguration"),
        NETTY("com.iuni.data.conf.client.NettyClientConfiguration"),
        ;

        private String clientConfigurationName;

        private ClientConfigurationType(String client) {
            clientConfigurationName = client;
        }

        public String getClientConfigurationName() {
            return this.clientConfigurationName;
        }

        public ClientConfiguration getConfiguration(String name) throws ConfigurationException {
            if (this.equals(ClientConfigurationType.OTHER)) {
                return new ClientConfiguration(name);
            }
            Class<? extends ClientConfiguration> clazz = null;
            ClientConfiguration instance = null;
            try {
                if (clientConfigurationName != null) {
                    clazz = (Class<? extends ClientConfiguration>) Class.forName(clientConfigurationName);
                    instance = clazz.getConstructor(String.class).newInstance(name);
                } else {
                    instance = new ClientConfiguration(name);
                    instance.setNotFoundConfigClass();
                }
            } catch (ClassNotFoundException e) {
                instance = new ClientConfiguration(name);
                instance.setNotFoundConfigClass();
            } catch (Exception e) {
                throw new ConfigurationException("Error creating configuration", e);
            }
            return instance;
        }
    }
}
