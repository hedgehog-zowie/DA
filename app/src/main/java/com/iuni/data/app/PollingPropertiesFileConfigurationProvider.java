package com.iuni.data.app;

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
 * Dynamic load config file
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PollingPropertiesFileConfigurationProvider extends PropertiesFileConfigurationProvider implements LifecycleAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollingPropertiesFileConfigurationProvider.class);

    private final EventBus eventBus;
    // config file
    private final File file;
    // the interval time to check config file
    private final int interval;
    // counter
    private final CounterGroup counterGroup;
    // lifecycle
    private LifecycleState lifecycleState;
    // scheduler
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
        // config file must be configured
        Preconditions.checkState(file != null, "The parameter file must not be null");
        // generate an scheduledExecutorService object, this object just have 1 thread, if the task is more than 1, they'll execute by order.
        executorService = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setNameFormat("conf-file-poller-%d").build());
        // read configured file
        FileWatcherRunnable fileWatcherRunnable = new FileWatcherRunnable(file, counterGroup);
        // execute task
        executorService.scheduleWithFixedDelay(fileWatcherRunnable, 0, interval, TimeUnit.SECONDS);

        lifecycleState = LifecycleState.START;

        LOGGER.debug("Configuration provider started");
    }

    @Override
    public void stop() {
        LOGGER.info("Configuration provider stopping");
        // stop executorService
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

    /**
     * watch config file
     */
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
