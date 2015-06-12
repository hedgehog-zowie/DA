package com.iuni.data.alipay;

import com.iuni.data.common.DateUtils;
import com.taobao.api.ApiException;
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
    private TopService topService;

    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(cron = "*/5 * * * * *")
    public void fetchData() throws ApiException {
        Date date = new Date();
        Date startDate = DateUtils.computeStartDate(date, -1);
        Date endDate = DateUtils.computeStartDate(date, 0);
        topService.getAliData(startDate, endDate);
    }

}
