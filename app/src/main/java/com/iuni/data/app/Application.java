package com.iuni.data.app;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.iuni.data.lifecycle.LifecycleAware;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            Options options = new Options();

            Option option = new Option("n", "name", true, "analyze代理名称");
            option.setRequired(true);
            options.addOption(option);

            option = new Option("f", "conf-file", true, "指定配置文件");
            option.setRequired(true);
            options.addOption(option);

            option = new Option(null, "no-reload-conf", false, "不自动加载配置文件");
            options.addOption(option);

//            option = new Option(null, "manual", false, "手动模式");
//            options.addOption(option);
//
//            option = new Option("s", "start", true, "手动模式 - 开始时间，开始时间需小于结束时间");
//            options.addOption(option);
//
//            option = new Option("e", "end", true, "手动模式 - 结束时间，结束时间需大于开始时间");
//            options.addOption(option);
//
//            option = new Option("t", "ttype", true, "手动模式 - 分析精度，按年(year)、月(month)、日(day)、时(hour)、分(minute)");
//            options.addOption(option);
//
//            option = new Option(null, "setBasicInfoForCreate-partition", false, "仅创建分区");
//            options.addOption(option);

            option = new Option("h", "help", false, "打印帮助");
            options.addOption(option);

            CommandLineParser parser = new GnuParser();
            CommandLine commandLine;
            try {
                commandLine = parser.parse(options, args);
            } catch (Exception e) {
                new HelpFormatter().printHelp("analyze agent", options, true);
                return;
            }

            if (commandLine.hasOption('h')) {
                new HelpFormatter().printHelp("analyze agent", options, true);
                return;
            }

            File configurationFile = new File(commandLine.getOptionValue('f'));
            String agentName = commandLine.getOptionValue('n');
            boolean reload = !commandLine.hasOption("no-reload-conf");

            if (!configurationFile.exists()) {
                String path = configurationFile.getPath();
                throw new ParseException("The specified configuration file does not exist: " + path);
            }

            ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
            List<LifecycleAware> components = Lists.newArrayList();
            TaskExecutor executor = (TaskExecutor) ctx.getBean("taskExecutor");
            if (reload) {
                EventBus eventBus = new EventBus(agentName + "-event-bus");
                PollingPropertiesFileConfigurationProvider configurationProvider =
                        new PollingPropertiesFileConfigurationProvider(agentName, configurationFile, eventBus, 30);
                components.add(configurationProvider);
                executor.setComponents(components);
                eventBus.register(executor);
            } else {
                PropertiesFileConfigurationProvider configurationProvider =
                        new PropertiesFileConfigurationProvider(agentName, configurationFile);
                executor.handleConfigurationEvent(configurationProvider.getConfiguration());
            }
            executor.start();

            final TaskExecutor appReference = executor;
            Runtime.getRuntime().addShutdownHook(new Thread("agent-shutdown-hook") {
                @Override
                public void run() {
                    appReference.stop();
                }
            });
        } catch (Exception e) {
            logger.error("A fatal error occurred while running. Exception follows.", e);
            System.exit(-1);
        }

    }

}
