import com.iuni.data.ws.controller.QueryController;
import net.sf.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {
    public static void main(String args[]) {
//        String data = "{\"type\": \"page\",\"time\": \"20141023140000\",\"adId\": \"103\",\"request\": \"www.test.com\",\"referer\": \"www.baidu.com\",\"vk\": \"abcvk\",\"uid\": \"myuid\",\"sid\": \"thisissid\",\"responseTime\": \"1\",\"pageSize\": \"1023\"}";
//        JSONObject jsonObject = JSONObject.fromObject(data);
//        PageData pageData = (PageData) JSONObject.toBean(jsonObject, PageData.class);
//        System.out.println(pageData);

        QueryController qc = new QueryController();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date startTime = calendar.getTime();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date endTime = calendar.getTime();
        System.out.println(startTime + " - " + endTime);
        qc.setTestData("", String.valueOf(startTime.getTime()), String.valueOf(endTime.getTime()));
    }
}
