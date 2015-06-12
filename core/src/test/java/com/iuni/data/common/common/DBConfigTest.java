package com.iuni.data.common.common;

import static org.junit.Assert.*;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DBConfigTest {
	
	private static PropertiesConfiguration config;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		config = new PropertiesConfiguration("jdbc.properties");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("oracle.jdbc.driver.OracleDriver", config.getString("jdbc.driver"));
		assertEquals("jdbc:oracle:thin:@18.8.5.116:1521:kfora01", config.getString("jdbc.url"));
		assertEquals("gionee_drs", config.getString("jdbc.username"));
		assertEquals("gionee_drstest", config.getString("jdbc.password"));
		assertEquals("10", config.getString("jdbc.poolsize"));
	}

}
