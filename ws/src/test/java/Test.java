import com.iuni.data.common.Constants;
import com.iuni.data.persist.mapper.system.SystemConstantsMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final String hBaseQuorum = "nn02.hadoop, dn01.hadoop, dn02.hadoop, dn03.hadoop, dn04.hadoop";
    private static final String tableName = "prd";
    private static final Configuration hBaseConf = HBaseConfiguration.create();
    private static final String rowKey = "20151222";
    private static final String f = "click";
    private static final String c = "pv-3.0000.0000";

    static {
        hBaseConf.set(Constants.hbaseQuorum, hBaseQuorum);
    }

    public static void main(String args[]) throws IOException, InterruptedException {
//        while (true) {
//            testBuriedPointData();
//        }
//        testEfficiency();
        testPageViewData();
    }

    private static void testEfficiency() throws IOException, InterruptedException {
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        testRWWithSameTable(100);
        end = System.currentTimeMillis();
        System.out.println("testRWWithSameTable\t" + (end - start) + "\ttestRWWithSameTable");

        start = System.currentTimeMillis();
        testRW(100);
        end = System.currentTimeMillis();
        System.out.println("testRW\t" + (end - start) + "\ttestRW");

        start = System.currentTimeMillis();
        testIncrement(100);
        end = System.currentTimeMillis();
        System.out.println("testIncrement\t" + (end - start) + "\ttestIncrement");

        start = System.currentTimeMillis();
        testIncrementWithSameTable(100);
        end = System.currentTimeMillis();
        System.out.println("testIncrementWithSameTable\t" + (end - start) + "\ttestIncrementWithSameTable");
    }

    private static void testRWWithSameTable(int n) throws IOException {
        HTable table = new HTable(hBaseConf, tableName);
        int i = 0;
        while (i < n) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(f), Bytes.toBytes(c));
            Result result = table.get(get);
            byte[] valueB = result.getValue(Bytes.toBytes(f), Bytes.toBytes(c));
            long value = Bytes.toLong(valueB);
//            System.out.println(value);
            // add
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(f), Bytes.toBytes(c), Bytes.toBytes(value + 1));
            table.put(put);
            i++;
        }
        table.close();
    }

    private static void testRW(int n) throws IOException {
        int i = 0;
        while (i < n) {
            HTable table = new HTable(hBaseConf, tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(f), Bytes.toBytes(c));
            Result result = table.get(get);
            byte[] valueB = result.getValue(Bytes.toBytes(f), Bytes.toBytes(c));
            long value = Bytes.toLong(valueB);
//            System.out.println(value);
            // add
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(f), Bytes.toBytes(c), Bytes.toBytes(value + 1));
            table.put(put);
            table.close();
            i++;
        }
    }

    private static void testIncrementWithSameTable(int n) throws IOException, InterruptedException {
        HTable table = new HTable(hBaseConf, tableName);
        int i = 0;
        while (i < n) {
            long v = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(f), Bytes.toBytes(c), 1);
//            System.out.println(v);
            i++;
        }
        table.close();
    }

    private static void testIncrement(int n) throws IOException, InterruptedException {
        int i = 0;
        while (i < n) {
            HTable table = new HTable(hBaseConf, tableName);
            long v = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(f), Bytes.toBytes(c), 1);
//            System.out.println(v);
            table.close();
            i++;
        }
    }

    private static void testBuriedPointData() throws IOException, InterruptedException {
        String qualifier = "3.0000.0000";
        HTable table = new HTable(hBaseConf, tableName);
        Get get = new Get(Bytes.toBytes("20151222"));
        Result result = table.get(get);
        Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes("click"));
        byte[] pv_value = familyMap.get(Bytes.toBytes("pv-" + qualifier));
        byte[] uv_value = familyMap.get(Bytes.toBytes("uv-" + qualifier));
        byte[] vv_value = familyMap.get(Bytes.toBytes("vv-" + qualifier));
        byte[] ip_value = familyMap.get(Bytes.toBytes("ip-" + qualifier));
        long pv = pv_value != null ? Bytes.toLong(pv_value) : 0;
        long uv = uv_value != null ? Bytes.toLong(uv_value) : 0;
        long vv = vv_value != null ? Bytes.toLong(vv_value) : 0;
        long ip = ip_value != null ? Bytes.toLong(ip_value) : 0;
        logger.info("pv:{}\tuv:{}\tvv:{}\tip:{}", pv, uv, vv, ip);
        Thread.sleep(1000);
    }

    private static void testPageViewData() throws IOException, InterruptedException {
        HTable table = new HTable(hBaseConf, tableName);
        Get get = new Get(Bytes.toBytes("20151231"));
        get.addColumn(Bytes.toBytes("pv"), Bytes.toBytes("pv-total"));
        get.addColumn(Bytes.toBytes("pv"), Bytes.toBytes("uv-total"));
        Result result = table.get(get);
        List<Cell> pvCellList = result.getColumnCells(Bytes.toBytes("pv"), Bytes.toBytes("pv-total"));
        System.out.println("======   pv   =======");
        for (Cell cell : pvCellList) {
            System.out.println(cell.getTimestamp() + " : " + Bytes.toLong(cell.getValue()));
        }
        List<Cell> uvCellList = result.getColumnCells(Bytes.toBytes("pv"), Bytes.toBytes("uv-total"));
        System.out.println("======   uv   =======");
        for (Cell cell : uvCellList) {
            System.out.println(cell.getTimestamp() + " : " + Bytes.toLong(cell.getValue()));
        }

        System.out.println(Bytes.toLong(result.getValue(Bytes.toBytes("pv"), Bytes.toBytes("pv-total"))));
        System.out.println(Bytes.toLong(result.getValue(Bytes.toBytes("pv"), Bytes.toBytes("uv-total"))));

    }

}
