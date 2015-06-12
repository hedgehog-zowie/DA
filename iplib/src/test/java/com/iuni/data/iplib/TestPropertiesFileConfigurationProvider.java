package com.iuni.data.iplib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iuni.data.conf.IpLibConfig;
import com.iuni.data.conf.IpLibConfig.AgentConfiguration;
import com.iuni.data.conf.ConfigurationError;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TestPropertiesFileConfigurationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestPropertiesFileConfigurationProvider.class);

    private static final File TESTFILE =
            new File(TestPropertiesFileConfigurationProvider.class.getClassLoader().getResource("ipLib.properties").getFile());

    private PropertiesFileConfigurationProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new PropertiesFileConfigurationProvider("test", TESTFILE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPropertyRead() throws Exception {
        IpLibConfig configuration = provider.getIpLibConfig();

        /*
        * Test the known errors in the file
        * */
//        List<String> expected2 = Lists.newArrayList();
//        expected2.add("t2 CONFIG_ERROR");
//        expected2.add("t2 AGENT_CONFIGURATION_INVALID");
//        expected2.add("t3 CONFIG_ERROR");
//        expected2.add("t3 PROPERTY_NAME_NULL");
//        expected2.add("t4 CONFIG_ERROR");
//        expected2.add("t4 AGENT_NAME_MISSING");
//        expected2.add("t5 CONFIG_ERROR");
//        expected2.add("t5 CONFIGURATION_KEY_ERROR");
//        expected2.add("t6 CONFIG_ERROR");
//        expected2.add("t6 DUPLICATE_PROPERTY");
//        expected2.add("t7 CONFIG_ERROR");
//        expected2.add("t7 INVALID_PROPERTY");
//        expected2.add("abc ATTRS_MISSING");
//        expected2.add("t8 CONFIG_ERROR");
//        expected2.add("t8 ILLEGAL_PROPERTY_NAME");
//        expected2.add("t9 CONFIG_ERROR");
//        expected2.add("t9 DEFAULT_VALUE_ASSIGNED");
//        List<String> actual2 = Lists.newArrayList();
//        for (ConfigurationError error : configuration.getConfigurationErrors()) {
//            actual2.add(error.getComponentName() + " " + error.getErrorType().toString());
//        }
//        Collections.sort(expected2);
//        Collections.sort(actual2);
//        Assert.assertEquals(expected2, actual2);

        AgentConfiguration agentConfiguration = configuration.getConfigurationFor("test");
        Assert.assertNotNull(agentConfiguration);

        LOGGER.info(agentConfiguration.getPrevalidationConfig());
        LOGGER.info(agentConfiguration.getPostvalidationConfig());

        Set<String> ipLibs = Sets.newHashSet("ipLibs");

        Assert.assertEquals(ipLibs, agentConfiguration.getIpLibSet());
    }

}
