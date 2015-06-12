package comliuni.data.flume;

import java.io.UnsupportedEncodingException;

import com.iuni.data.flume.IuniRowKeyGenerator;
import org.apache.hadoop.hbase.util.Bytes;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class IuniRowKeyGeneratorTest {
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
	
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	@Test
	@PerfTest(invocations = 100)
	public void testReverseTimestampKey(){
		System.out.println(Bytes.toString(IuniRowKeyGenerator.getReverseTimestampKey("1398875255000")));
	}
	
	@Test
	@PerfTest(invocations = 100)
	public void testNanoTimestampKey() throws UnsupportedEncodingException{
		System.out.println(Bytes.toString(IuniRowKeyGenerator.getNanoTimestampKey("prefix")));
	}
	
	@Test
	@PerfTest(invocations = 100)
	public void testTimestampKey() throws UnsupportedEncodingException{
		System.out.println(Bytes.toString(IuniRowKeyGenerator.getTimestampKey("prefix")));
	}
	
	@Test
	@PerfTest(invocations = 100)
	public void testRandomKey() throws UnsupportedEncodingException{
		System.out.println(Bytes.toString(IuniRowKeyGenerator.getRandomKey("prefix")));
	}
	
	@Test
	@PerfTest(invocations = 100)
	public void testUUIDKey() throws UnsupportedEncodingException{
		System.out.println(Bytes.toString(IuniRowKeyGenerator.getUUIDKey("prefix")));
	}
}
