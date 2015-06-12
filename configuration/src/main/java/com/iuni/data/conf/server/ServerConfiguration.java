package com.iuni.data.conf.server;

import com.iuni.data.Context;
import com.iuni.data.conf.ComponentConfiguration;
import com.iuni.data.conf.ConfigurationException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ServerConfiguration extends ComponentConfiguration {

    public ServerConfiguration(String componentName) {
        super(componentName);
    }

    @Override
    public void configure(Context context) throws ConfigurationException {
        super.configure(context);
    }

    public enum ServerConfigurationType {
        OTHER(null),
        HTTP("com.iuni.data.conf.server.HttpServerConfiguration"),
        NETTY("com.iuni.data.conf.server.HttpServerConfiguration");

        private String serverConfigurationName;

        private ServerConfigurationType(String server) {
            serverConfigurationName = server;
        }

        public String getServerConfigurationName() {
            return this.serverConfigurationName;
        }

        public ServerConfiguration getConfiguration(String name) throws ConfigurationException {
            if (this.equals(ServerConfigurationType.OTHER)) {
                return new ServerConfiguration(name);
            }
            Class<? extends ServerConfiguration> clazz = null;
            ServerConfiguration instance = null;
            try {
                if (serverConfigurationName != null) {
                    clazz = (Class<? extends ServerConfiguration>) Class.forName(serverConfigurationName);
                    instance = clazz.getConstructor(String.class).newInstance(name);
                } else {
                    instance = new ServerConfiguration(name);
                    instance.setNotFoundConfigClass();
                }
            } catch (ClassNotFoundException e) {
                instance = new ServerConfiguration(name);
                instance.setNotFoundConfigClass();
            } catch (Exception e) {
                throw new ConfigurationException("Error creating configuration", e);
            }
            return instance;
        }
    }

}
