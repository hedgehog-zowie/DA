package com.iuni.data.hbase.sindex;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.HRegion;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IUNI secondary index
 * <p/>
 * startkey + indexName + ${index column value} + ${rowkey}
 *
 * @author Nicholas
 */
public class IuniIndexRegionObserve extends BaseRegionObserver {
    private static final Logger logger = LoggerFactory.getLogger(IuniIndexRegionObserve.class);
    private static final String familyName = "f";
    private static final String columnNameTime = HbaseColumns.COLUMN_TIME.getName();
    private static final String columnNameUser = HbaseColumns.COLUMN_USER.getName();
    private static final String columnNameReq = HbaseColumns.COLUMN_REQUEST_URL.getName();
    private static final List<String> columnNames = new ArrayList();
    private static final String basicIndexName = "idx";
    private static final String indexColumnName = "value";
    private static final String basicTableName = "Idx";
    private static final byte[] incrementRow = Bytes.toBytes("totalRow");
    private static final byte[] incrementCf = Bytes.toBytes("f");
    private static final byte[] incrementCName = Bytes.toBytes("iCol");
    private static final Configuration conf = new Configuration();
    private static final int DEFAULT_THREAD_NUM = 10;
    private static BlockingQueue<List<Put>> timeQueue = new LinkedBlockingQueue();
    private static BlockingQueue<List<Put>> userQueue = new LinkedBlockingQueue();
    private static BlockingQueue<List<Put>> reqQueue = new LinkedBlockingQueue();
    // todo add uv + time idx
    // todo add sid idx , value is referer
    private static final ExecutorService timeExecutor = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    private static final ExecutorService userExecutor = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
    private static final ExecutorService reqExecutor = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);

    static {
        columnNames.add(columnNameTime);
        columnNames.add(columnNameUser);
        columnNames.add(columnNameReq);

        int i = 0;
        while (i != DEFAULT_THREAD_NUM) {
            logger.info("start thread: {}", "timePutThread-" + i);
            timeExecutor.execute(new TimePutThread("timePutThread-" + i));
            logger.info("start thread: {}", "userPutThread-" + i);
            userExecutor.execute(new UserPutThread("userPutThread-" + i));
            logger.info("start thread: {}", "reqPutThread-" + i);
            reqExecutor.execute(new ReqPutThread("reqPutThread-" + i));
            i++;
        }
    }

    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability)
            throws IOException {
        HRegion region = e.getEnvironment().getRegion();
        String startKey = Bytes.toString(region.getStartKey());
        if (startKey.isEmpty()) startKey = "00000000";
        for (String columnName : columnNames) {
            List<Cell> kv = put.get("f".getBytes(), columnName.getBytes());
            if (kv.size() != 0) {
                List<Put> putList = Lists.newArrayListWithCapacity(kv.size());
                Iterator<Cell> kvItor = kv.iterator();
                StringBuilder sbr = new StringBuilder().append(startKey).append(columnName);
                boolean putFlag = true;
                while (kvItor.hasNext()) {
                    Cell tmp = kvItor.next();
                    String valueStr = Bytes.toString(tmp.getValue());
                    if (valueStr.isEmpty()) {
                        putFlag = false;
                        break;
                    }
                    String rowKey = sbr.append(basicIndexName).append(valueStr).append(Bytes.toString(tmp.getRow())).toString();
                    logger.debug("index row key: {}", rowKey);
                    Put indexPut = new Put(rowKey.getBytes());
                    indexPut.add("f".getBytes(), indexColumnName.getBytes(), tmp.getRow());
                    putList.add(indexPut);
                }
                if (putFlag) {
                    if (columnNameTime.equals(columnName)) {
                        timeQueue.add(putList);
                    } else if (columnNameUser.equals(columnName)) {
                        userQueue.add(putList);
                    } else if (columnNameReq.equals(columnName)) {
                        reqQueue.add(putList);
                    }
                }
            }
        }
    }

    static class TimePutThread extends Thread {
        private static final Logger logger = LoggerFactory.getLogger(TimePutThread.class);
        private HTable timeIdxTable;
        private boolean timeInterruptFlag = false;

        TimePutThread(String name) {
            try {
                this.timeIdxTable = new HTable(IuniIndexRegionObserve.conf, IuniIndexRegionObserve.columnNameTime + basicTableName);
            } catch (IOException e) {
                logger.error("new htable error, idxTable name is: {}", IuniIndexRegionObserve.columnNameTime + basicTableName);
                this.timeInterruptFlag = true;
            }
            setName(name);
        }

        public void run() {
            logger.info("{} start.", getName());
            while (!this.timeInterruptFlag) {
                try {
                    List<Put> putList = (List) IuniIndexRegionObserve.timeQueue.take();
                    try {
                        this.timeIdxTable.put(putList);
                        this.timeIdxTable.incrementColumnValue(IuniIndexRegionObserve.incrementRow, IuniIndexRegionObserve.incrementCf, IuniIndexRegionObserve.incrementCName, 1L);
                    } catch (RetriesExhaustedWithDetailsException e) {
                        logger.error("{}: put idx to timeIdxTable error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.timeInterruptFlag = false;
                    } catch (InterruptedIOException e) {
                        logger.error("{}: put idx to timeIdxTable error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.timeInterruptFlag = false;
                    } catch (IOException e) {
                        logger.error("{}: setBasicInfoForUpdate timeIdxTable totalRow error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.timeInterruptFlag = false;
                    }
                } catch (InterruptedException e) {
                    logger.error("{}: take put from timeQueue error. error: {}", getName(), e);
                    this.timeInterruptFlag = false;
                }
            }
            logger.info("{} exit with error.", getName());
        }
    }

    static class UserPutThread extends Thread {
        private static final Logger logger = LoggerFactory.getLogger(UserPutThread.class);
        private HTable userIdxTable;
        private boolean userInterruptFlag = false;

        UserPutThread(String name) {
            try {
                this.userIdxTable = new HTable(IuniIndexRegionObserve.conf, IuniIndexRegionObserve.columnNameUser + basicTableName);
            } catch (IOException e) {
                logger.error("new hTable error, idxTable name is: {}", IuniIndexRegionObserve.columnNameUser + basicTableName);
                this.userInterruptFlag = true;
            }
            setName(name);
        }

        public void run() {
            logger.info("{} start.", getName());
            while (!this.userInterruptFlag) {
                try {
                    List<Put> putList = (List) IuniIndexRegionObserve.userQueue.take();
                    try {
                        this.userIdxTable.put(putList);
                        this.userIdxTable.incrementColumnValue(IuniIndexRegionObserve.incrementRow, IuniIndexRegionObserve.incrementCf, IuniIndexRegionObserve.incrementCName, 1L);
                    } catch (RetriesExhaustedWithDetailsException e) {
                        logger.error("{}: put idx to userIdxTable error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.userInterruptFlag = false;
                    } catch (InterruptedIOException e) {
                        logger.error("{}: put idx to userIdxTable error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.userInterruptFlag = false;
                    } catch (IOException e) {
                        logger.error("{}: setBasicInfoForUpdate timeIdxTable totalRow error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.userInterruptFlag = false;
                    }
                } catch (InterruptedException e) {
                    logger.error("{}: take put from userQueue error. error: {}", getName(), e);
                    this.userInterruptFlag = false;
                }
            }
            logger.info("{} exit with error.", getName());
        }
    }

    static class ReqPutThread extends Thread {
        private static final Logger logger = LoggerFactory.getLogger(ReqPutThread.class);
        private HTable reqIdxTable;
        private boolean reqInterruptFlag = false;

        ReqPutThread(String name) {
            try {
                this.reqIdxTable = new HTable(IuniIndexRegionObserve.conf, IuniIndexRegionObserve.columnNameReq + basicTableName);
            } catch (IOException e) {
                logger.error("new htable error, idxTable name is: {}", IuniIndexRegionObserve.columnNameReq + basicTableName);
                this.reqInterruptFlag = true;
            }
            setName(name);
        }

        public void run() {
            logger.info("{} start.", getName());
            while (!this.reqInterruptFlag) {
                try {
                    List<Put> putList = (List) IuniIndexRegionObserve.reqQueue.take();
                    try {
                        this.reqIdxTable.put(putList);
                        this.reqIdxTable.incrementColumnValue(IuniIndexRegionObserve.incrementRow, IuniIndexRegionObserve.incrementCf, IuniIndexRegionObserve.incrementCName, 1L);
                    } catch (RetriesExhaustedWithDetailsException e) {
                        logger.error("{}: put idx to reqIdxTable error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.reqInterruptFlag = false;
                    } catch (InterruptedIOException e) {
                        logger.error("{}: put idx to reqIdxTable error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.reqInterruptFlag = false;
                    } catch (IOException e) {
                        logger.error("{}: setBasicInfoForUpdate timeIdxTable totalRow error. idx row: {}, error: {}", new Object[]{getName(), Bytes.toString(((Put) putList.get(0)).getRow()), e});
                        this.reqInterruptFlag = false;
                    }
                } catch (InterruptedException e) {
                    logger.error("{}: take put from reqQueue error. error: {}", getName(), e);
                    this.reqInterruptFlag = false;
                }
            }
            logger.info("{} exit with error.", getName());
        }
    }
}
