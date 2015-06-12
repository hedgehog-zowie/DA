package com.iuni.data.iplib;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.iuni.data.IpLib;
import com.iuni.data.lifecycle.LifecycleAware;
import com.iuni.data.lifecycle.LifecycleState;
import com.iuni.data.lifecycle.LifecycleSupervisor;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final List<LifecycleAware> components;
    private final LifecycleSupervisor supervisor;
    private MaterializedConfiguration materializedConfiguration;
//    private final EventBus ipEventBus;

    public Application() {
        this(new ArrayList<LifecycleAware>(0));
    }

    public Application(List<LifecycleAware> components) {
        this.components = components;
        supervisor = new LifecycleSupervisor();
//        ipEventBus = new EventBus();
    }

    public synchronized void start() {
        for (LifecycleAware component : components) {
            supervisor.supervise(component, new LifecycleSupervisor.SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);
        }
    }

    public synchronized void stop() {
        supervisor.stop();
    }

    @Subscribe
    public synchronized void handleConfigurationEvent(MaterializedConfiguration conf) {
        stopAllComponents();
        startAllComponents(conf);
    }

    private void startAllComponents(MaterializedConfiguration materializedConfiguration) {
        logger.info("Starting new configuration:{}", materializedConfiguration);
        for (Map.Entry<String, IpLib> entry : materializedConfiguration.getIpLibs().entrySet()) {
            try {
                logger.info("Starting IpLib " + entry.getKey());
//                ipEventBus.register(entry.getValue());
                supervisor.supervise(entry.getValue(), new LifecycleSupervisor.SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);
            } catch (Exception e) {
                logger.error("Error while starting {}", entry.getValue(), e);
            }
        }
        // wait all ipLibs start
        for (IpLib ipLib : materializedConfiguration.getIpLibs().values()) {
            // is not start and is not error
            while (ipLib.getLifecycleState() != LifecycleState.START && !supervisor.isComponentInErrorState(ipLib)) {
                try {
                    logger.info("Waiting for ipLib: " + ipLib.getName() + " to start. Sleeping for 1000 ms");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("Interrupted while waiting for ipLib to start.", e);
                    Throwables.propagate(e);
                }
            }
        }
        // set this.materializedConfiguration
        this.materializedConfiguration = materializedConfiguration;
    }

    private void stopAllComponents() {
        if (this.materializedConfiguration != null) {
            logger.info("Shutting down configuration: {}", this.materializedConfiguration);
            for (Map.Entry<String, IpLib> entry : this.materializedConfiguration.getIpLibs().entrySet()) {
                try {
                    logger.info("Stopping IpLib " + entry.getKey());
//                    ipEventBus.unregister(entry.getValue());
                    supervisor.unsupervise(entry.getValue());
                } catch (Exception e) {
                    logger.error("Error while stopping {}", entry.getValue(), e);
                }
            }
        }
    }

    private void exec() throws IOException {
        // wait start
        while (materializedConfiguration == null) {
            try {
                logger.info("Waiting for components to start. Sleeping for 1000 ms");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for ipLib to start.", e);
                Throwables.propagate(e);
            }
        }
        // receive ip
        ServerSocket socket = new ServerSocket(4444);
        Socket connection = socket.accept();
        String ipStr;
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        while ((ipStr = in.readLine()) != null) {
            StringBuilder osb = new StringBuilder();
            for (Map.Entry<String, IpLib> ipLibEntry : materializedConfiguration.getIpLibs().entrySet()) {
                IpInfo ipInfo = ipLibEntry.getValue().getIpInfo(ipStr);
                if (!(ipStr.equals(ipInfo.getIp()))) {
                    logger.error(ipStr + " not get info");
                }
                osb.append(ipLibEntry.getKey()).append(":").append(ipInfo).append("\n");
            }
            connection.getOutputStream().write(osb.toString().getBytes("gb2312"));
//            out.println(osb.toString());
        }
    }

    public static void main(String[] args) {
        try {
            Options options = new Options();

            Option option = new Option("n", "name", true, "the name of this agent");
            option.setRequired(true);
            options.addOption(option);

            option = new Option("f", "conf-file", true, "specify a conf file");
            option.setRequired(true);
            options.addOption(option);

            option = new Option(null, "no-reload-conf", false, "do not reload conf file if changed");
            options.addOption(option);

            option = new Option("h", "help", false, "display help text");
            options.addOption(option);

            CommandLineParser parser = new GnuParser();
            CommandLine commandLine = parser.parse(options, args);

            File configurationFile = new File(commandLine.getOptionValue('f'));
            String agentName = commandLine.getOptionValue('n');
            boolean reload = !commandLine.hasOption("no-reload-conf");

            if (commandLine.hasOption('h')) {
                new HelpFormatter().printHelp("ipLib agent", options, true);
                return;
            }

            if (!configurationFile.exists()) {
                String path = configurationFile.getPath();
                throw new ParseException("The specified configuration file does not exist: " + path);
            }

            List<LifecycleAware> components = Lists.newArrayList();
            Application application;
            if (reload) {
                EventBus eventBus = new EventBus(agentName + "-event-bus");
                PollingPropertiesFileConfigurationProvider configurationProvider =
                        new PollingPropertiesFileConfigurationProvider(agentName, configurationFile, eventBus, 30);
                components.add(configurationProvider);
                application = new Application(components);
                eventBus.register(application);
            } else {
                PropertiesFileConfigurationProvider configurationProvider =
                        new PropertiesFileConfigurationProvider(agentName, configurationFile);
                application = new Application();
                application.handleConfigurationEvent(configurationProvider.getConfiguration());
            }
            application.start();

            application.exec();

            final Application appReference = application;
            Runtime.getRuntime().addShutdownHook(new Thread("agent-shutdown-hook") {
                @Override
                public void run() {
                    appReference.stop();
                }
            });

        } catch (Exception e) {
            logger.error("A fatal error occurred while running. Exception follows.", e);
        }
    }
}
