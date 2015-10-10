package com.iuni.data.weixin;

import com.iuni.data.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@EnableScheduling
@ImportResource("classpath:spring-service.xml")
public class ScheduledTasks {

    private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private WeixinService weixinService;

    @Scheduled(cron = "0 0 23 * * *")
//    @Scheduled(cron = "*/5 * * * * *")
    public void fetchData() {
        Date date = new Date();
        Date startDate = DateUtils.computeStartDate(date, -1);
        Date endDate = DateUtils.computeStartDate(date, 0);
        weixinService.getPayData(startDate, endDate);
    }

}
