package com.iuni.data.alipay;

import com.google.common.base.Preconditions;
import com.iuni.data.common.Constants;
import com.iuni.data.utils.DateUtils;
import com.taobao.api.ApiException;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public void start(CommandLine commandLine) throws ApiException {
        boolean manual = commandLine.hasOption("manual");
        if (manual) {
            String startDateStr = commandLine.getOptionValue('s');
            String endDateStr = commandLine.getOptionValue('e');
            Date startDate = DateUtils.simpleDateStrToDate(startDateStr);
            Date endDate = DateUtils.simpleDateStrToDate(endDateStr);
            Preconditions.checkState(endDate.getTime() > startDate.getTime(), "End time must be greater than start time.");
            Preconditions.checkState(startDate != null && endDate != null, "The parameter "
                    + Constants.manualStart + " or " + Constants.manualEnd + " is not correct.");

            ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-service.xml");
            TopService topService = (TopService) ctx.getBean("topService");
            topService.getPayData(startDate, endDate);
        } else
            SpringApplication.run(ScheduledTasks.class);
    }

    public static void main(String[] args) throws ApiException, IOException {

        try {
            Options options = new Options();

            Option option = new Option(null, "manual", false, "手动模式");
            options.addOption(option);

            option = new Option("s", "start", true, "手动模式 - 开始时间（包括），开始时间需小于结束时间，格式为：yyyyMMddHHmmss");
            options.addOption(option);

            option = new Option("e", "end", true, "手动模式 - 结束时间(不包括)，结束时间需大于开始时间，格式为：yyyyMMddHHmmss");
            options.addOption(option);

            option = new Option("h", "help", false, "打印帮助");
            options.addOption(option);

            CommandLineParser parser = new GnuParser();
            CommandLine commandLine;
            try {
                commandLine = parser.parse(options, args);
            } catch (Exception e) {
                new HelpFormatter().printHelp("fetch alipay data", options, true);
                return;
            }

            if (commandLine.hasOption('h')) {
                new HelpFormatter().printHelp("fetch alipay data", options, true);
                return;
            }

            Application application = new Application();
            application.start(commandLine);
        } catch (Exception e) {
            logger.error("A fatal error occurred while running. Exception follows.", e);
            System.exit(-1);
        }

    }

}
