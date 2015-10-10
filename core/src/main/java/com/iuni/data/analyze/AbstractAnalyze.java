package com.iuni.data.analyze;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iuni.data.Analyze;
import com.iuni.data.Context;
import com.iuni.data.common.Constants;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.conf.Configurable;
import com.iuni.data.lifecycle.LifecycleAware;
import com.iuni.data.lifecycle.LifecycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class AbstractAnalyze implements Analyze, LifecycleAware, Configurable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAnalyze.class);

    private String name;

    private LifecycleState lifecycleState;

    // manual config
    protected boolean manual;
    protected Date startDate;
    protected Date endDate;

    // scheduler config
    protected CronTrigger trigger;
    protected CronSequenceGenerator sequenceGenerator;
    protected TaskScheduler taskScheduler;
    protected final Map<String, ScheduledFuture<?>> scheduledFutures;

    // analyze time type config
    protected TType tType;
    // setBasicInfoForCreate partition config
    protected boolean createPartition;

    public AbstractAnalyze() {
        this.lifecycleState = LifecycleState.IDLE;
        scheduledFutures = new HashMap<>();
    }

    @Override
    public void start() {
        lifecycleState = LifecycleState.START;
        if (manual)
            analyze(startDate, endDate, tType, createPartition);
        else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    endDate = DateUtils.transDateByTType(sequenceGenerator.next(new Date()), tType);
                    switch (tType) {
                        case DAY:
                            endDate = new Date(endDate.getTime() - 24 * 60 * 60 * 1000);
                            startDate = new Date(endDate.getTime() - 24 * 60 * 60 * 1000);
                            break;
                        case HOUR:
                            endDate = new Date(endDate.getTime() - 60 * 60 * 1000);
                            startDate = new Date(endDate.getTime() - 60 * 60 * 1000);
                            break;
                        case MINUTE:
                            endDate = new Date(endDate.getTime() - 60 * 1000);
                            startDate = new Date(endDate.getTime() - 60 * 1000);
                            break;
                        default:
                            endDate = new Date(endDate.getTime() - 24 * 60 * 60 * 1000);
                            startDate = new Date(endDate.getTime() - 24 * 60 * 60 * 1000);
                            break;
                    }
                    analyze(startDate, endDate, tType, createPartition);
                }
            };
            scheduledFutures.put(name, taskScheduler.schedule(runnable, trigger));
        }
    }

    @Override
    public void stop() {
        lifecycleState = LifecycleState.STOP;
        for (Map.Entry<String, ScheduledFuture<?>> scheduledFuture : scheduledFutures.entrySet()) {
            scheduledFuture.getValue().cancel(false);
            scheduledFutures.remove(scheduledFuture.getKey());
            logger.info("stop analyze named : [ {} ]", scheduledFuture.getKey());
        }
    }

    @Override
    public void configure(Context context) {
        // time type
        String tTypeStr = context.getString(Constants.timeType, Constants.TIME_TYPE_DEFAULT);
        tType = TType.valueOf(tTypeStr.toUpperCase());
        Preconditions.checkState(tType.getValue() == Calendar.DATE || tType.getValue() == Calendar.HOUR || tType.getValue() == Calendar.MINUTE,
                "The parameter " + Constants.timeType + " must be one of day/hour/minute");

        // manual config
        manual = context.getBoolean(Constants.manual, Constants.MANUAL_DEFAULT);
        if (manual) {
            String startDateStr = context.getString(Constants.manualStart);
            String endDateStr = context.getString(Constants.manualEnd);
            startDate = DateUtils.transDateByTType(startDateStr, tType);
            endDate = DateUtils.transDateByTType(endDateStr, tType);
            Preconditions.checkState(endDate.getTime() > startDate.getTime(), "End time must be greater than start time.");
            Preconditions.checkState(startDate != null && endDate != null, "The parameter "
                    + Constants.manualStart + " or " + Constants.manualEnd + " is not correct.");
        } else {
            // schedule config
            String cronStr = context.getString(Constants.cron);
            Preconditions.checkState(cronStr != null, "The parameter " + Constants.cron + " must be specified");
            trigger = new CronTrigger(cronStr);
            sequenceGenerator = new CronSequenceGenerator(cronStr, TimeZone.getDefault());

            // thread number
            int threadNum = context.getInteger(Constants.thread, Constants.THREAD_NUM_DEFAULT);
            taskScheduler = new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(threadNum,
                    new ThreadFactoryBuilder().setNameFormat(name + "-analyze-poller-%d").build()));
        }
    }

    @Override
    public LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
