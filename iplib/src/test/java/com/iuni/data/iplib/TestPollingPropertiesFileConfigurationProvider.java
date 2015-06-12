package com.iuni.data.iplib;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.Files;
import com.iuni.data.IpLib;
import com.iuni.data.lifecycle.LifecycleController;
import com.iuni.data.lifecycle.LifecycleState;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TestPollingPropertiesFileConfigurationProvider {
    private static final File TESTFILE = new File(
            TestPollingPropertiesFileConfigurationProvider.class.getClassLoader().getResource("ipLib.properties").getFile());

    private PollingPropertiesFileConfigurationProvider provider;
    private File baseDir;
    private File configFile;
    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {

        baseDir = Files.createTempDir();

        configFile = new File(baseDir, TESTFILE.getName());
        Files.copy(TESTFILE, configFile);

        eventBus = new EventBus("testEventBus");
        provider = new PollingPropertiesFileConfigurationProvider("test", configFile, eventBus, 1);
        provider.start();
        LifecycleController.waitForOneOf(provider, LifecycleState.START_OR_ERROR);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(baseDir);
        provider.stop();
    }

    @Test
    public void testPolling() throws Exception {

        Thread.sleep(2000L);

        final List<MaterializedConfiguration> events = Lists.newArrayList();

        Object eventHandler = new Object() {
            @Subscribe
            public synchronized void handleConfigurationEvent(MaterializedConfiguration event) {
                events.add(event);
                for(Map.Entry<String, IpLib> entry : event.getIpLibs().entrySet()){
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        };
        eventBus.register(eventHandler);
        configFile.setLastModified(System.currentTimeMillis());

        Thread.sleep(2000L);

        Assert.assertEquals(String.valueOf(events), 1, events.size());

        MaterializedConfiguration materializedConfiguration = events.remove(0);

        Assert.assertEquals(1, materializedConfiguration.getIpLibs().size());

    }

}
