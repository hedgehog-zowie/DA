package com.iuni.data.hbase;

import com.iuni.data.common.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("hbaseOperator")
public class HBaseOperator {

    private static final Logger logger = LoggerFactory.getLogger(HBaseOperator.class);

    private String hBaseQuorum;
    private String tableName;

    private final Configuration hBaseConf;
    private HConnection hConnection;

    public HBaseOperator() {
        hBaseConf = HBaseConfiguration.create();
    }

    public HBaseOperator(Configuration hbaseConf) {
        this.hBaseConf = hbaseConf;
    }

    public HBaseOperator(String hbaseQuorum) {
        hBaseConf = HBaseConfiguration.create();
        this.hBaseQuorum = hbaseQuorum;
    }

    private void checkConnection() throws IOException {
        if (hConnection == null || hConnection.isClosed())
            hConnection = HConnectionManager.createConnection(hBaseConf);
    }

    public List<Result> query(String tableName, Scan scan) {
        List<Result> resultList = new ArrayList<>();
        HTable table = null;
        ResultScanner resultScanner = null;
        try {
            table = new HTable(hBaseConf, tableName);
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

    /**
     * 获取单独一个单元格数据
     *
     * @param rowKey
     * @param familyName
     * @param columnName
     * @return
     */
    public Result getCell(String rowKey, String familyName, String columnName) {
        Result result = null;
        HTable table = null;
        try {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
            table = new HTable(hBaseConf, tableName);
            result = table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * 获取一整行的数据
     *
     * @param rowKey
     * @return
     */
    public Result getRow(String rowKey) {
        Result result = null;
        HTable table = null;
        try {
            table = new HTable(hBaseConf, tableName);
            result = table.get(new Get(Bytes.toBytes(rowKey)));
        } catch (IOException e) {
            logger.error("get HTable error, HTable is: {}, rowKey is {}, error msg is: {}", tableName, rowKey, e.getLocalizedMessage());
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
            }
        }
        return result;
    }

    /**
     * get whole row
     *
     * @param rowKeyList
     * @return
     */
    public Map<String, Result> getRows(Collection<String> rowKeyList) {
        Map<String, Result> resultMap = new HashMap<>();
        HTableInterface table = null;
        try {
            checkConnection();
            table = hConnection.getTable(tableName);
//            table = new HTable(hBaseConf, tableName);
            for (String rowKey : rowKeyList) {
                Result result = table.get(new Get(Bytes.toBytes(rowKey)));
                resultMap.put(rowKey, result);
            }
        } catch (IOException e) {
            logger.error("get HTable error, HTable is: {}, error msg is: {}", tableName, e.getLocalizedMessage());
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
            }
        }
        return resultMap;
    }

    /**
     * 获取数据
     *
     * @param getMap
     * @return
     */
    public Map<String, Result> get(Map<String, Get> getMap) {
        Map<String, Result> resultMap = new HashMap<>();
        HTableInterface table = null;
        try {
            checkConnection();
            table = hConnection.getTable(tableName);
            for (Map.Entry<String, Get> get : getMap.entrySet())
                resultMap.put(get.getKey(), table.get(get.getValue()));
        } catch (IOException e) {
            logger.error("get HTable error, HTable is: {}, error msg is: {}", tableName, e.getLocalizedMessage());
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
            }
        }
        return resultMap;
    }

    /**
     * 获取数据
     *
     * @param get
     * @return
     */
    public Result get(Get get) {
        Result result = null;
        HTableInterface table = null;
        try {
            checkConnection();
            table = hConnection.getTable(tableName);
            result = table.get(get);
        } catch (IOException e) {
            logger.error("get HTable error, HTable is: {}, error msg is: {}", tableName, e.getLocalizedMessage());
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
            }
        }
        return result;
    }

    /**
     * 计数器增加
     *
     * @param rowKey
     * @param cf
     * @param qualifier
     * @return
     */
    public long addCounter(String rowKey, String cf, String qualifier) {
        long result = 0l;
        HTable table = null;
        try {
            table = new HTable(hBaseConf, tableName);
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

    public String gethBaseQuorum() {
        return hBaseQuorum;
    }

    public void setHBaseQuorum(String hbaseQuorum) {
        this.hBaseQuorum = hbaseQuorum;
        hBaseConf.set(Constants.hbaseQuorum, this.hBaseQuorum);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}
