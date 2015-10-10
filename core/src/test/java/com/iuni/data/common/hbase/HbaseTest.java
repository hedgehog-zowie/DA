package com.iuni.data.common.hbase;

import com.iuni.data.hbase.HexStringSplit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class HbaseTest {

    private static Configuration conf;
    private static HTable table;
    private static HTable timeIdxTable;

    private static final String tableName = "iunilog";
    private static final String idxTableName = "timeIdx";
    private static byte[][] splitKeys;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.3.0-cdh5.0.0");
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "dn04.hadoop");
        try {
            splitKeys = HexStringSplit.split(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * 字符串按字典顺序加1
     * @param string
     * @return
     */
    private String stringAddByCharacter(String string){
        char[] newChar = new char[string.length()];
        int i = 0;
        int len = string.length();
        while (i < len) {
            // 32(space 空格) - 126(~)
            char c = string.charAt(i);
            if(i == (string.length() - 1) && c >= 32 && c <= 126)
                c += 1;
            newChar[i] = c;
            i++;
        }
        return new String(newChar);
    }

    @Test
    public void scanTable() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("00000000");
        for (byte[] splitKey : splitKeys) {
            System.out.println(Bytes.toString(splitKey));
            stringList.add(Bytes.toString(splitKey));
        }
        System.out.println("splitKeys.length:" + splitKeys.length);
        Map<String, String> timeMap = new HashMap<String, String>();
        String sKey = "1111111111";
        String eKey = stringAddByCharacter("1111111112");
        for(String keyString : stringList) {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(keyString + "timeidx" + sKey));
            scan.setStopRow(Bytes.toBytes(keyString + "timeidx" + eKey));
            try {
                timeIdxTable = new HTable(conf, idxTableName);
                ResultScanner scanner = timeIdxTable.getScanner(scan);
                for (Result result : scanner) {
                    byte[] rowKey = result.getValue(Bytes.toBytes("f"), Bytes.toBytes("value"));
                    Get get = new Get(rowKey);
                    table = new HTable(conf, tableName);
                    Result resultRow = table.get(get);
                    byte[] time = resultRow.getValue(Bytes.toBytes("f"), Bytes.toBytes("time"));
                    timeMap.put(Bytes.toString(rowKey), Bytes.toString(time));
                    table.close();
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Map.Entry<String, String> entry : timeMap.entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
