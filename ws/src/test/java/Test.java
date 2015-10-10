import com.iuni.data.common.Constants;
import com.iuni.data.hbase.field.CommonField;
import com.iuni.data.ws.common.Config;
import com.iuni.data.hbase.field.ClickField;
import com.iuni.data.ws.controller.QueryController;
import com.iuni.data.ws.dto.Click;
import com.iuni.data.ws.dto.PV;
import com.iuni.data.ws.dto.PageResult;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {
    static final QueryController queryController = new QueryController();

    public static void main(String args[]) throws IOException {

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
//        query1225();

        // 查询1228号上线的活动
        // query1228();

        // 查询官网改版新埋点
        testNewSite();

//        testCounter();
    }

    private static void queryDouble12() {
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
        for (Map.Entry<String, Integer> entry : clickMap.entrySet()) {
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

    private static void testNewSite() throws IOException {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2015, 8, 30, 0, 0, 0);
        Date startTime = calendar.getTime();
        calendar.set(2015, 8, 30, 23, 59, 59);
        Date endTime = calendar.getTime();

        long start = System.currentTimeMillis();

        HTable table = new HTable(Config.getConf(), Config.getTableName());

        System.out.println("new htable:" + (System.currentTimeMillis() - start));
//        String startRow = "cgi" + "1442160000000";
        String startRow = "click" + startTime.getTime();
//        String stopRow = "cgi" + "1442246400000";
        String stopRow = "click" + endTime.getTime();
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setStopRow(Bytes.toBytes(stopRow));
        scan.setBatch(Constants.default_batch_size);
        scan.setCaching(Constants.default_catch_size);
        scan.setCacheBlocks(Constants.default_catch_blocks);
//        scan.setMaxVersions();
        ResultScanner resultScanner = table.getScanner(scan);
//        long c = 0;
        Map<String, Integer> pvMap = new TreeMap<>();
        Map<String, Set<String>> uvMap = new TreeMap<>();
        Map<String, Set<String>> vvMap = new TreeMap<>();
        Map<String, Set<String>> ipMap = new TreeMap<>();
        for (Result result : resultScanner) {
//            c++;
//            System.out.println(result);
//            String s1 = "";
//            try{
//                s1 = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("s1")));
//            }catch (Exception e){
//                ;
//            }
//            String s2 = "";
//            try{
//                s2 = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("s2")));
//            }catch (Exception e){
//                ;
//            }
//            String s3 = "";
//            try {
//                s3 = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("s3")));
//            }catch (Exception e){
//                ;
//            }
//            String adId = "";
//            try{
//                adId = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes("adId")));
//            }catch (Exception e){
//                ;
//            }

            String rtag = "";
            try {
                rtag = Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes(ClickField.rTag.getRealFiled())));
                if (rtag.contains("3.")) {
                    int pv = pvMap.get(rtag) == null ? 0 : pvMap.get(rtag);
                    pvMap.put(rtag, pv + 1);
                    Set<String> uvSet = (uvMap.get(rtag) == null ? new HashSet<String>() : uvMap.get(rtag));
                    uvSet.add(Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes(CommonField.VK.getRealFiled()))));
                    uvMap.put(rtag, uvSet);
                    Set<String> vvSet = (vvMap.get(rtag) == null ? new HashSet<String>() : vvMap.get(rtag));
                    vvSet.add(Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes(CommonField.SID.getRealFiled()))));
                    vvMap.put(rtag, vvSet);
                    Set<String> ipSet = (ipMap.get(rtag) == null ? new HashSet<String>() : ipMap.get(rtag));
                    ipSet.add(Bytes.toString(result.getValue(Bytes.toBytes("f"), Bytes.toBytes(CommonField.IP.getRealFiled()))));
                    ipMap.put(rtag, ipSet);
                }
            } catch (Exception e) {
            }
        }
        resultScanner.close();
        table.close();
        System.out.println("pv:");
        for (Map.Entry<String, Integer> entry : pvMap.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }
        System.out.println("uv:");
        for (Map.Entry<String, Set<String>> entry : uvMap.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue().size());
        }
        System.out.println("vv:");
        for (Map.Entry<String, Set<String>> entry : vvMap.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue().size());
        }
        System.out.println("ip:");
        for (Map.Entry<String, Set<String>> entry : ipMap.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue().size());
        }

        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
    }

    public static void testCounter() throws IOException {
        HTable uvTable = new HTable(Config.getConf(), Bytes.toBytes("uv"));
        Get get = new Get(Bytes.toBytes("xxxxuvxxxx"));
        Result result = uvTable.get(get);
        byte[] value = result.getValue(Bytes.toBytes("pv"), Bytes.toBytes("20150101"));
        if (value != null)
            System.out.println("uv xxxxuvxxxx-20150101 c:" + Bytes.toLong(value));
        else
            System.out.println("no c");
    }

}
