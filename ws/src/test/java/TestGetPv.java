import com.iuni.data.ws.controller.QueryController;
import com.iuni.data.ws.dto.Click;
import com.iuni.data.ws.dto.PV;
import com.iuni.data.ws.dto.PageResult;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TestGetPv {
    static final QueryController queryController = new QueryController();

    public static void main(String args[]) {

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 29, 0, 0, 0);
//        Date startTime = calendar.getTime();
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 29, 23, 59, 59);
//        Date endTime = calendar.getTime();
        // pv
//        int totalPv = 0;
//        List<PageResult> pvList = queryController.getData("pv", startTime.getTime(), endTime.getTime());
//        Collections.sort(pvList);
//        for (PageResult result : pvList) {
//            if(((PV)result).getUrl().contains("mobile/wx/2014/1228/index.html")) {
//                System.out.println(result.toString());
//                totalPv += ((PV) result).getPv();
//            }
//        }
//        System.out.println("========== total pv:" + totalPv + " ===========");
        // uv
//        int totalUv = 0;
//        List<PageResult> uvList = queryController.getData("uv", startTime.getTime(), endTime.getTime());
//        Collections.sort(uvList);
//        for (PageResult result : uvList) {
//            if(((UV)result).getUrl().contains("mobile/wx/2014/1228/index.html")) {
//                System.out.println(result.toString());
//                totalUv += ((UV) result).getUv();
//            }
//        }
//        System.out.println("========== total uv:" + totalUv + " ===========");

        // 查询双12
//        queryDouble12();

        // 查询1225上线活动PV
        query1225();

        // 查询1228号上线的活动
        // query1228();

    }

    private static void queryDouble12(){
        Calendar calendar = Calendar.getInstance();

        // 1211
        calendar.set(2014, 11, 11, 0, 0, 0);
        Date startTime = calendar.getTime();
        calendar.set(2014, 11, 11, 23, 59, 59);
        Date endTime = calendar.getTime();
        queryDouble12(startTime, endTime);

        // 1212
        calendar.set(2014, 11, 12, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2014, 11, 12, 23, 59, 59);
        endTime = calendar.getTime();
        queryDouble12(startTime, endTime);

        // 1213
        calendar.set(2014, 11, 13, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2014, 11, 13, 23, 59, 59);
        endTime = calendar.getTime();
        queryDouble12(startTime, endTime);
    }

    private static void queryDouble12(Date startTime, Date endTime) {
        Map<String, Integer> clickMap = new HashMap<String, Integer>();
        clickMap.put("2.0016.0039", 0);
        clickMap.put("2.0016.0040", 0);
        clickMap.put("2.0016.0041", 0);
        clickMap.put("2.0016.0042", 0);
        clickMap.put("2.0016.0043", 0);
        clickMap.put("2.0016.0044", 0);
        clickMap.put("2.0016.0045", 0);
        clickMap.put("2.0016.0046", 0);
        List<PageResult> clickList = queryController.getData("click", startTime.getTime(), endTime.getTime());
        Collections.sort(clickList);
        for (PageResult result : clickList) {
            if ("2.0016.0039".equals(((Click) result).getrTag()) ||
                    "2.0016.0040".equals(((Click) result).getrTag()) ||
                    "2.0016.0041".equals(((Click) result).getrTag()) ||
                    "2.0016.0042".equals(((Click) result).getrTag()) ||
                    "2.0016.0043".equals(((Click) result).getrTag()) ||
                    "2.0016.0044".equals(((Click) result).getrTag()) ||
                    "2.0016.0045".equals(((Click) result).getrTag()) ||
                    "2.0016.0046".equals(((Click) result).getrTag())) {
                clickMap.put(((Click) result).getrTag(), (int) (clickMap.get(((Click) result).getrTag()) + ((Click) result).getCount()));
            }
        }
        for(Map.Entry<String, Integer> entry : clickMap.entrySet()){
            System.out.println(String.format("===%s %s:\t%d ===", startTime, entry.getKey(), entry.getValue()));
        }
    }

    private static void query1225() {
        String page = "2014/1225/christmas.html";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, 11, 20, 0, 0, 0);
        Date startTime = calendar.getTime();
        calendar.set(2014, 11, 31, 23, 59, 59);
        Date endTime = calendar.getTime();

        int pv = 0;
        List<PageResult> clickList = queryController.getData("pv", startTime.getTime(), endTime.getTime());
        Collections.sort(clickList);
        for (PageResult result : clickList) {
            if (((PV) result).getUrl().contains(page)) {
                System.out.println(result.toString());
                pv += ((PV) result).getPv();
            }
        }

        System.out.println("==========" + page + ":\t" + pv + " ===========");
    }

    private static void query1228() {
        Calendar calendar = Calendar.getInstance();

        // 29
        calendar.set(2014, 11, 29, 0, 0, 0);
        Date startTime = calendar.getTime();
        calendar.set(2014, 11, 29, 23, 59, 59);
        Date endTime = calendar.getTime();
        query1228(startTime, endTime);

        // 30
        calendar.set(2014, 11, 30, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2014, 11, 30, 23, 59, 59);
        endTime = calendar.getTime();
        query1228(startTime, endTime);

        // 31
        calendar.set(2014, 11, 31, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2014, 11, 31, 23, 59, 59);
        endTime = calendar.getTime();
        query1228(startTime, endTime);

        // 1
        calendar.set(2015, 0, 1, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2015, 0, 1, 23, 59, 59);
        endTime = calendar.getTime();
        query1228(startTime, endTime);

        // 2
        calendar.set(2015, 0, 2, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2015, 0, 2, 23, 59, 59);
        endTime = calendar.getTime();
        query1228(startTime, endTime);

        // 3
        calendar.set(2015, 0, 3, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2015, 0, 3, 23, 59, 59);
        endTime = calendar.getTime();
        query1228(startTime, endTime);

        // 4
        calendar.set(2015, 0, 4, 0, 0, 0);
        startTime = calendar.getTime();
        calendar.set(2015, 0, 4, 23, 59, 59);
        endTime = calendar.getTime();
        query1228(startTime, endTime);

    }

    private static void query1228(Date startTime, Date endTime) {
        int click = 0;
        List<PageResult> clickList = queryController.getData("click", startTime.getTime(), endTime.getTime());
        Collections.sort(clickList);
        for (PageResult result : clickList) {
            if (((Click) result).getUrl().contains("1228")) {
                System.out.println(result.toString());
                click += ((Click) result).getCount();
            }
        }
        System.out.println("==========" + startTime + ":\t" + click + " ===========");
    }

}
