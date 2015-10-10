package com.iuni.data.impala;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.iuni.data.exceptions.IuniDAImpalaException;

public class ConnectorTest {

	private static final String IMPALAD_HOST = "18.8.5.130";

	private static final String IMPALAD_JDBC_PORT = "21050";

	private static final String CONNECTION_URL = "jdbc:hive2://" + IMPALAD_HOST
			+ ':' + IMPALAD_JDBC_PORT + "/;auth=noSasl";

	private ImpalaConnector myConnector;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testConnect() throws IuniDAImpalaException {
		myConnector = new ImpalaConnector(CONNECTION_URL);
//		assertEquals(true, myConnector.isConnected());
	}

	@Test
	public void testReConnect() throws IuniDAImpalaException {
		myConnector = new ImpalaConnector(CONNECTION_URL);
//		assertEquals(true, myConnector.reconnect());
//		assertEquals(true, myConnector.isConnected());
	}

}
