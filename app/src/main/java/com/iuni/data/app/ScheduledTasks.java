package com.iuni.data.app;

import com.iuni.data.Analyze;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.config.RTagType;
import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.repository.config.ChannelRepository;
import com.iuni.data.persist.repository.config.PageTagRepository;
import com.iuni.data.persist.repository.config.PageTagTypeRepository;
import com.iuni.data.persist.repository.webkpi.ClickWebKpiRepository;
import com.iuni.data.persist.repository.webkpi.PageWebKpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
//@Configuration
//@EnableAutoConfiguration
//@EnableScheduling
//@ImportResource("classpath:applicationContext.xml")
@Deprecated
public class ScheduledTasks {
    private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PageTagRepository pageTagRepository;
    @Autowired
    private PageTagTypeRepository pageTagTypeRepository;
    @Autowired
    private PageWebKpiRepository pageWebKpiRepository;
    @Autowired
    private ClickWebKpiRepository clickWebKpiRepository;

    private final Lock lock = new ReentrantLock();

    // type 4 is click
    private final Long RTAG_TYPE_ID_OF_CLICK = 4L;

    private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private JpaTransactionManager transactionManager = (JpaTransactionManager) context.getBean("transactionManager");

    /**
     * select channel and rTag
     */
    private boolean init(Analyze analyze) {
        List<Channel> channelList = channelRepository.findByStatusAndCancelFlag(1, 0);
        if (channelList.size() == 0) {
            logger.info("no channel configured");
        }
        Map<String, Channel> channelMap = new HashMap<>();
        for (Channel channel : channelList)
            channelMap.put(channel.getCode(), channel);

        RTagType rTagType = pageTagTypeRepository.findOne(RTAG_TYPE_ID_OF_CLICK);

        List<RTag> rTagList = pageTagRepository.findByStatusAndCancelFlag(1, 0);
        if (rTagList.size() == 0)
            logger.info("no rTag configured.");
        Map<String, RTag> rTagMap = new HashMap<>();
        for (RTag rTag : rTagList) {
            if (rTag.getrTagType().getId() == rTagType.getId())
                // key is code + url
                rTagMap.put(rTag.getRtag() + rTag.getInfo(), rTag);
        }
//        analyze.init(rTagMap, rTagType, channelMap);

        return true;
    }

    /**
     * delete old data and save new page and click kpi data
     *
     * @param timeRangeMap
     * @param tType
     */
    @Transactional
    public void saveKpi(final Map<Date, Date> timeRangeMap, final TType tType, final Map<String, PageWebKpi> pageWebKpiMap, final Map<String, ClickWebKpi> clickWebKpiMap) {
        lock.lock();
        try {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
            transactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus transactionStatus) {
                    // delete all data by time and tType
                    for (Map.Entry<Date, Date> entry : timeRangeMap.entrySet()) {
                        Date time = entry.getKey();
                        pageWebKpiRepository.delete(pageWebKpiRepository.findByTimeAndTtype(time, tType.getPattern()));
                        clickWebKpiRepository.delete(clickWebKpiRepository.findByTimeAndTtype(time, tType.getPattern()));
                    }
                    Date date = new Date();
                    // save data
                    Map<String, Channel> channelMap = new HashMap<>();
                    for (PageWebKpi pageWebKpi : pageWebKpiMap.values()) {
                        pageWebKpi.setCreateDate(date);
                        // save channel
                        Channel channel = pageWebKpi.getChannel();
                        if (!channelMap.containsKey(channel.getCode())) {
                            Channel existChannel = channelRepository.findByCode(channel.getCode());
                            if (existChannel != null)
                                channel = existChannel;
                            else
                                channel = channelRepository.save(channel);
                            channelMap.put(channel.getCode(), channel);
                        }
                        pageWebKpi.setChannel(channelMap.get(channel.getCode()));
                    }
                    // Map<String, RTag> rTagMap = new HashMap<>();
                    for (ClickWebKpi clickWebKpi : clickWebKpiMap.values()) {
                        clickWebKpi.setCreateDate(date);
                        // save channel
                        Channel channel = clickWebKpi.getChannel();
                        if (!channelMap.containsKey(channel.getCode())) {
                            Channel existChannel = channelRepository.findByCode(channel.getCode());
                            if (existChannel != null)
                                channel = existChannel;
                            else
                                channel = channelRepository.save(channel);
                            channelMap.put(channel.getCode(), channel);
                        }
                        clickWebKpi.setChannel(channelMap.get(channel.getCode()));
                    }
                    pageWebKpiRepository.save(pageWebKpiMap.values());
                    clickWebKpiRepository.save(clickWebKpiMap.values());
//                    transactionManager.commit(transactionStatus);
                    return null;
                }
            });
        } finally {
            lock.unlock();
        }
    }

//    private void saveKpi(Result result) {
//        if (result.getPageWebKpiMap() != null && result.getPageWebKpiMap().size() != 0
//                && result.getClickWebKpiMap() != null && result.getClickWebKpiMap().size() != 0)
//            saveKpi(result.getTimeRangeMap(), result.gettType(), result.getPageWebKpiMap(), result.getClickWebKpiMap());
//    }

    @Scheduled(cron = "0 0 0 * * *")
    public void partition() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 1 * 24 * 60 * 60 * 1000);
        logger.info("setBasicInfoForCreate partition every day, start:{}, end:{}", startDate, endDate);
        exec(startDate, endDate, TType.DAY, true);
    }

    //    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
//    @Scheduled(cron = "0 * * * * *")

    /**
     * 延迟5分钟执行，以免日志临时文件未改名
     */
    @Scheduled(cron = "0 5 0 * * *")
    public void analyzeDay() {
        Date date = new Date();
        // 5分钟前的那一天的0点0分
        Date endDate = DateUtils.computeStartDate(new Date(date.getTime() - 5 * 60 * 1000), 0);
        Date startDate = new Date(endDate.getTime() - 1 * 24 * 60 * 60 * 1000);
        logger.debug("exec every day, start:{}, end:{}", startDate, endDate);
        exec(startDate, endDate, TType.DAY, true);
    }

    /**
     * 延迟5分钟执行，以免日志临时文件未改名
     */
    @Scheduled(cron = "0 5 * * * *")
    public void execEveryHour() {
        Date date = new Date();
        // 5分钟前的那一个小时的0分
        Date endDate = DateUtils.computeStartHour(new Date(date.getTime() - 5 * 60 * 1000), 0);
        Date startDate = new Date(endDate.getTime() - 1 * 60 * 60 * 1000);
        logger.debug("exec every hour, start:{}, end:{}", startDate, endDate);
        exec(startDate, endDate, TType.HOUR, true);
    }

    @Scheduled(cron = "0 * * * * *")
    public void execEveryMinute() {
        Date date = new Date();
        // 分析5分钟前的那一分钟
        Date endDate = new Date(date.getTime() - 5 * 60 * 1000);
        Date startDate = new Date(endDate.getTime() - 1 * 60 * 1000);
        logger.debug("exec every minute, start:{}, end:{}", startDate, endDate);
        exec(startDate, endDate, TType.MINUTE, true);
    }

    private void exec(final Date startDate, final Date endDate, TType tType, boolean createPartition) {
        // wait components start
//        while (Application.materializedConfiguration == null) {
//            try {
//                logger.info("Waiting for components to start. Sleeping for 1000 ms");
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                logger.error("Interrupted while waiting for ipLib to start.", e);
//                Throwables.propagate(e);
//            }
//        }
//        ExecThread execThread = new ExecThread(startDate, endDate, tType, createPartition);
//        execThread.start();
    }

    class ExecThread extends Thread {

        private Date startDate;
        private Date endDate;
        private TType tType;
        // just setBasicInfoForCreate partition
        private boolean createPartition;

        public ExecThread(Date startDate, Date endDate, TType tType, boolean createPartition) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.tType = tType;
            this.createPartition = createPartition;
        }

        @Override
        public void run() {
//            for (Map.Entry<String, Analyze> analyzeEntry : Application.materializedConfiguration.getAnalyzes().entrySet()) {
//                Analyze analyze = analyzeEntry.getValue();
//                if (!analyze.getInit()) {
//                    logger.info("init channel and rtag for analyze.{}", analyze);
//                    init(analyze);
//                }
//
////                TType tType = TType.MINUTE;
////                boolean createPartition = true;
//                logger.info("analyze of {} {}-{} start, start time is: {}", tType.getPattern(), IuniDateUtils.dateToSimpleDateStr(startDate), IuniDateUtils.dateToSimpleDateStr(endDate), new Date());
//                AnalyzeResult analyzeResult;
//                analyzeResult = analyze.analyze(startDate, endDate, tType, createPartition);
//                logger.info("analyze of {} {}-{} finished, finish time is: {}", tType.getPattern(), IuniDateUtils.dateToSimpleDateStr(startDate), IuniDateUtils.dateToSimpleDateStr(endDate), new Date());
//                saveKpi(analyzeResult);
//
//            }
        }
    }

}
