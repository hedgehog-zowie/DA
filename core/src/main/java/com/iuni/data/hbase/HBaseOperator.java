package com.iuni.data.hbase;

import com.iuni.data.common.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class HBaseOperator {

    private static final Logger logger = LoggerFactory.getLogger(HBaseOperator.class);

    private final Configuration hbaseConf;

    public HBaseOperator(Configuration hbaseConf) {
        this.hbaseConf = hbaseConf;
    }

    public HBaseOperator(String hbaseQuorum) {
        hbaseConf = HBaseConfiguration.create();
        hbaseConf.set(Constants.hbaseQuorum, hbaseQuorum);
    }

    public List<Result> query(String tableName, Scan scan) {
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
                    logger.error("close hbase table error, ", e);
                }
            }
        }
        return resultList;
    }

}
