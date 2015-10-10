package com.iuni.data.common.common;

import com.iuni.data.utils.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.exceptions.IuniDADateFormatException;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.*;

import java.util.Date;
import java.util.Map;

public class IuniDateUtilTest {
	
	private static String datestr = "01/May/2014:14:25:50 +0800 ";

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
//	@PerfTest(invocations = 100, threads = 10, duration = 1000)
//	@Required(max = 100, average = 10)
	public void test() throws IuniDADateFormatException {
		Date date = DateUtils.logDateStrToDate(datestr);
		System.out.println(date.getTime());
		System.out.println(System.currentTimeMillis());
//		System.out.println(date);
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		System.out.println(calendar.getTime());
//		System.out.println(calendar.get(Calendar.MONTH) + "==" + calendar.get(Calendar.HOUR_OF_DAY));
//		System.out.println(Calendar.getInstance().get(Calendar.MONTH) + "==" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}

    @Test
    public void testParseTimeRange(){
        System.out.println("=============YEAR==============");
        String startTimeStr = "20140125000023";
        String endTimeStr = "20160225123300";
        TType tType = TType.YEAR;
        Map<Date, Date> timeRangeMap = DateUtils.parseTimeRange(startTimeStr, endTimeStr, tType);
        for(Map.Entry<Date, Date> entry: timeRangeMap.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        System.out.println("=============MONTH==============");
        startTimeStr = "20141125000023";
        endTimeStr = "20150103001101";
        tType = TType.MONTH;
        timeRangeMap = DateUtils.parseTimeRange(startTimeStr, endTimeStr, tType);
        for(Map.Entry<Date, Date> entry: timeRangeMap.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        System.out.println("=============DAY==============");
        startTimeStr = "20140101235923";
        endTimeStr = "20140103001101";
        tType = TType.DAY;
        timeRangeMap = DateUtils.parseTimeRange(startTimeStr, endTimeStr, tType);
        for(Map.Entry<Date, Date> entry: timeRangeMap.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        System.out.println("=============HOUR==============");
        startTimeStr = "20140101235923";
        endTimeStr = "20140102031236";
        tType = TType.HOUR;
        timeRangeMap = DateUtils.parseTimeRange(startTimeStr, endTimeStr, tType);
        for(Map.Entry<Date, Date> entry: timeRangeMap.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        System.out.println("=============MINUTE==============");
        startTimeStr = "20140101235923";
        endTimeStr = "20140102000304";
        tType = TType.MINUTE;
        timeRangeMap = DateUtils.parseTimeRange(startTimeStr, endTimeStr, tType);
        for(Map.Entry<Date, Date> entry: timeRangeMap.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

}
