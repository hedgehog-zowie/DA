package com.iuni.data.hbase;

import com.iuni.data.common.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("hbaseOperator")
public class HBaseOperator {

    private static final Logger logger = LoggerFactory.getLogger(HBaseOperator.class);

    private String hbaseQuorum;

    private final Configuration hbaseConf;

    public HBaseOperator() {
        hbaseConf = HBaseConfiguration.create();
    }

    public HBaseOperator(Configuration hbaseConf) {
        this.hbaseConf = hbaseConf;
    }

    public HBaseOperator(String hbaseQuorum) {
        hbaseConf = HBaseConfiguration.create();
        this.hbaseQuorum = hbaseQuorum;
    }

    public List<Result> query(String tableName, Scan scan) {
        hbaseConf.set(Constants.hbaseQuorum, hbaseQuorum);
        List<Result> resultList = new ArrayList<>();
        HTable table = null;
        ResultScanner resultScanner = null;
        try {
            table = new HTable(hbaseConf, tableName);
            resultScanner = table.getScanner(scan);
            for (Result result : resultScanner)
                resultList.add(result);
        } catch (IOException e) {
            logger.error("scan HTable error, HTable is: {}, error msg is: {}", tableName, e.getLocalizedMessage());
        } finally {
            if (resultScanner != null) {
                resultScanner.close();
            }
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
            }
        }
        return resultList;
    }

    public long addCounter(String tableName, String rowKey, String cf, String qualifier) {
        hbaseConf.set(Constants.hbaseQuorum, hbaseQuorum);
        long result = 0l;
        HTable table = null;
        try {
            table = new HTable(hbaseConf, tableName);
            result = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(cf), Bytes.toBytes(qualifier), 1);
        } catch (IOException e) {
            logger.error("get counter error, HTable is: {}, error msg is: {}", tableName, e.getLocalizedMessage());
        } finally {
            if (table != null)
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
        }
        return result;
    }

    public String getHbaseQuorum() {
        return hbaseQuorum;
    }

    public void setHbaseQuorum(String hbaseQuorum) {
        this.hbaseQuorum = hbaseQuorum;
    }
}
