import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class TestHalfHour {

    private DateFormat sdf;
    private final long halfMills = 30 * 60 * 1000;

    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyyMMddHHmm");
    }

    @After
    public void tearDown(){
    }

    private String getNextHalfHour(String timeStr) throws ParseException {
        Date time = sdf.parse(timeStr);
        long newTimeStamp = (time.getTime() / halfMills + 1) * halfMills;
        Date newTime = new Date(newTimeStamp);
        return sdf.format(newTime);
    }

    @Test
    public void test() throws ParseException {
        assertEquals("201512311130", getNextHalfHour("201512311129"));
        assertEquals("201512311200", getNextHalfHour("201512311130"));
        assertEquals("201512311200", getNextHalfHour("201512311135"));
        assertEquals("201512311230", getNextHalfHour("201512311200"));
        assertEquals("201601010000", getNextHalfHour("201512312359"));
    }

}
