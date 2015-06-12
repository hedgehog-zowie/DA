package com.iuni.data.iplib.pure;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IPSeekperTest {
	
	private static IPSeeker ipSeeker;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ipSeeker = IPSeeker.getInstance();
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
	public void test() throws IOException {
		System.out.println(ipSeeker.getArea("113.106.195.231"));
		System.out.println(ipSeeker.getLocation("113.106.195.231"));
	}

}
