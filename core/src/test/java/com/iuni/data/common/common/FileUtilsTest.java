package com.iuni.data.common.common;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.iuni.data.common.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtilsTest {

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
	public void test() throws IOException {
		FileUtils.updateFile(123, 0, "asdf.dat");
		FileUtils.updateFile(456, 8, "asdf.dat");
//		FileUtils.updateFile(System.currentTimeMillis(), 0, 4, "asdf.dat");
		RandomAccessFile randomFile = new RandomAccessFile("asdf.dat", "r");
        randomFile.seek(0);
        System.out.println(randomFile.readLong());
        randomFile.seek(8);
        System.out.println(randomFile.readLong());
        randomFile.close();
	}

}
