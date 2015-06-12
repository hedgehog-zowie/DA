package com.iuni.data.iplib;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iuni.data.common.CounterGroup;
import com.iuni.data.lifecycle.LifecycleAware;
import com.iuni.data.lifecycle.LifecycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 动态加载配置文件
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PollingPropertiesFileConfigurationProvider extends PropertiesFileConfigurationProvider implements LifecycleAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollingPropertiesFileConfigurationProvider.class);

    private final EventBus eventBus;
    // 配置文件
    private final File file;
    // 检查配置文件的时间间隔
    private final int interval;
    // 计数器
    private final CounterGroup counterGroup;
    // 生命周期状态
    private LifecycleState lifecycleState;
    // 调度器
    private ScheduledExecutorService executorService;

    public PollingPropertiesFileConfigurationProvider(String agentName, File file, EventBus eventBus, int interval) {
        super(agentName, file);
        this.eventBus = eventBus;
        this.file = file;
        this.interval = interval;
        counterGroup = new CounterGroup();
        lifecycleState = LifecycleState.IDLE;
    }

    @Override
    public void start() {
        LOGGER.info("Configuration provider starting");
        // 文件不能为空
        Preconditions.checkState(file != null, "The parameter file must not be null");
        // 产生一个ScheduledExecutorService对象，这个对象的线程池大小为1，若任务多于一个，任务将按先后顺序执行
        executorService = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setNameFormat("conf-file-poller-%d").build());
        // 配置文件读取
        FileWatcherRunnable fileWatcherRunnable = new FileWatcherRunnable(file, counterGroup);
        // 安排所提交的Runnable任务在每次执行完后，等待interval所指定的时间后重复执行
        executorService.scheduleWithFixedDelay(fileWatcherRunnable, 0, interval, TimeUnit.SECONDS);

        lifecycleState = LifecycleState.START;

        LOGGER.debug("Configuration provider started");
    }

    @Override
    public void stop() {
        LOGGER.info("Configuration provider stopping");
        // 停止executorService
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                LOGGER.debug("Waiting for file watcher to terminate");
            }
        } catch (InterruptedException e) {
            LOGGER.debug("Interrupted while waiting for file watcher to terminate");
            Thread.currentThread().interrupt();
        }
        lifecycleState = LifecycleState.STOP;
        LOGGER.debug("Configuration provider stopped");
    }

    @Override
    // 同步获取生命周期状态
    public synchronized LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public String toString() {
        return "PollingPropertiesFileConfigurationProvider{" +
                "eventBus=" + eventBus +
                ", file=" + file +
                ", interval=" + interval +
                ", counterGroup=" + counterGroup +
                ", lifecycleState=" + lifecycleState +
                ", executorService=" + executorService +
                '}';
    }

    public class FileWatcherRunnable implements Runnable {

        private final File file;
        private final CounterGroup counterGroup;

        private long lastChange;

        public FileWatcherRunnable(File file, CounterGroup counterGroup) {
            super();
            this.file = file;
            this.counterGroup = counterGroup;
            this.lastChange = 0L;
        }

        @Override
        public void run() {
            LOGGER.debug("Checking file:{} for changes", file);

            counterGroup.incrementAndGet("file.checks");

            long lastModified = file.lastModified();

            if (lastModified > lastChange) {
                LOGGER.info("Reloading configuration file:{}", file);

                counterGroup.incrementAndGet("file.loads");

                lastChange = lastModified;

                try {
                    eventBus.post(getConfiguration());
                } catch (Exception e) {
                    LOGGER.error("Failed to load configuration data. Exception follows.", e);
                } catch (NoClassDefFoundError e) {
                    LOGGER.error("Failed to start agent because dependencies were not found in classpath. Error follows.", e);
                } catch (Throwable t) {
                    // caught because the caller does not handle or log Throwables
                    LOGGER.error("Unhandled error", t);
                }
            }
        }
    }
}
