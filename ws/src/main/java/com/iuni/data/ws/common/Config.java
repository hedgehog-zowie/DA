package com.iuni.data.ws.common;

import com.iuni.data.common.Constants;
import com.iuni.data.ws.util.PropertiesUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Config {

    private static Configuration conf;
    private static final byte[] tableName = Bytes.toBytes(PropertiesUtil.get(Constants.pageReportDataTable, Constants.pageReportDataTableDefault));
    private static final byte[] cfName = Bytes.toBytes(PropertiesUtil.get(Constants.pageReportDataTableCf, Constants.pageReportDataTableCfDefault));
    private static final String column = PropertiesUtil.get(Constants.pageReportDataTableColumn, Constants.pageReportDataTableColumnDefault);

    static {
        String hbaseQuorum = PropertiesUtil.get(Constants.hbaseQuorum);
        if(hbaseQuorum != null) {
            conf = HBaseConfiguration.create();
            conf.set(Constants.hbaseQuorum, hbaseQuorum);
        }
    }

    public static Configuration getConf() {
        return conf;
    }

    public static byte[] getTableName() {
        return tableName;
    }

    public static byte[] getCfName() {
        return cfName;
    }

    public static String getColumn() {
        return column;
    }
}