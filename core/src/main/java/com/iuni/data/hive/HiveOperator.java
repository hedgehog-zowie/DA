package com.iuni.data.hive;

import com.iuni.data.common.DateUtils;
import com.iuni.data.exceptions.IuniDAImpalaException;
import com.iuni.data.exceptions.IuniDADateFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * 操作HIVE
 */
public class HiveOperator {
    private static Logger logger = LoggerFactory.getLogger(HiveOperator.class);

    private HiveConnector connector;

    private HiveOperator() {
    }

    public HiveOperator(HiveConnector connector) throws IuniDAImpalaException {
        this.connector = connector;
        if (!this.connector.isConnected())
            this.connector.reconnect();
    }

    public void addJar(String jarPath) {
        try {
            Statement stmt = this.connector.getConn().createStatement();
            stmt.execute("add jar " + jarPath);
            stmt.close();
        } catch (SQLException e) {
            logger.error("add jar error, addCmd: {}", jarPath);
        }
    }

    /**
     * 添加分区信息，时间格式为：yyyyMMddHHmmss
     *
     * @param timeStr
     * @throws IuniDADateFormatException
     */
    private synchronized void parseAndAddPartition(String tableName, String timeStr) throws IuniDADateFormatException {
        String addPartitionStr = "ALTER TABLE %s ADD IF NOT EXISTS PARTITION (time = %s)";
        addPartitionStr = String.format(addPartitionStr, tableName, timeStr);
        try {
            Statement stmt = this.connector.getConn().createStatement();
            stmt.execute(addPartitionStr);
            stmt.close();
            logger.info("add partition: {}", timeStr);
        } catch (SQLException e) {
            logger.error("add partition error, partition time is: {}", timeStr, e);
        }
    }

    /**
     * 添加分区信息
     *
     * @param date
     */
    public void parseAndAddPartition(String tableName, Date date) throws IuniDADateFormatException {
        String parDateStr = DateUtils.dateToSimpleDateStrOfDay(date);
        parseAndAddPartition(tableName, parDateStr);
    }

    /**
     * 批量添加分区信息
     *
     * @param startDate
     * @param endDate
     * @throws IuniDADateFormatException
     */
    public void parseAndAddPartitions(String tableName, Date startDate, Date endDate) throws IuniDADateFormatException {
        // add partition every minute
        long cur = DateUtils.computeStartDate(startDate, 0).getTime();
        while (cur < endDate.getTime()) {
            // 分区日期
            Date parDate = new Date(cur);
            parseAndAddPartition(tableName, parDate);
            // add cur
            cur += 1 * 24 * 60 * 60 * 1000;
        }
    }

    /**
     * 批量添加分区信息，时间格式为：yyyyMMddHHmmss
     *
     * @param startTime
     * @param endTime
     */
    public void parseAndAddPartitions(String tableName, String startTime, String endTime) throws IuniDADateFormatException {
        Date startDate = DateUtils.simpleDateStrToDate(startTime);
        Date endDate = DateUtils.simpleDateStrToDate(endTime);
        parseAndAddPartitions(tableName, startDate, endDate);
    }

    /**
     * drop partition , timeStr is like yyyyMMddHHmmss
     *
     * @param tableName
     * @param timeStr
     * @throws IuniDADateFormatException
     */
    public void parseAndDelPartition(String tableName, String timeStr) throws IuniDADateFormatException {
        String dropPartitionStr = "ALTER TABLE %s DROP IF EXISTS PARTITION (time = %s)";
        dropPartitionStr = String.format(dropPartitionStr, tableName, timeStr);
        try {
            Statement stmt = this.connector.getConn().createStatement();
            stmt.execute(dropPartitionStr);
            stmt.close();
        } catch (SQLException e) {
            logger.error("drop partition error, partition time is: {}, err msg: {}", timeStr, e.getLocalizedMessage());
        }
    }

    /**
     * drop partition
     *
     * @param tableName
     * @param parDate
     * @throws IuniDADateFormatException
     */
    public void parseAndDelPartition(String tableName, Date parDate) throws IuniDADateFormatException {
        String parDateStr = DateUtils.dateToSimpleDateStrOfDay(parDate);
        parseAndDelPartition(tableName, parDateStr);
    }

    /**
     * batch delete partitions
     *
     * @param tableName
     * @param startDate
     * @param endDate
     * @throws IuniDADateFormatException
     */
    public void parseAndDelPartitions(String tableName, Date startDate, Date endDate) throws IuniDADateFormatException {
        long cur = DateUtils.computeStartDate(startDate, 0).getTime();
        while (cur < endDate.getTime()) {
            // partition time
            Date parDate = new Date(cur);
            parseAndDelPartition(tableName, parDate);
            cur += 1 * 24 * 60 * 60 * 1000;
        }
    }

    /**
     * batch delete partitions，timeStr is like yyyyMMddHHmmss
     *
     * @param tableName
     * @param startTime
     * @param endTime
     */
    public void parseAndDelPartitions(String tableName, String startTime, String endTime) throws IuniDADateFormatException {
        // delete partition every minute
        Date startDate = DateUtils.simpleDateStrToDate(startTime);
        Date endDate = DateUtils.simpleDateStrToDate(endTime);
        parseAndDelPartitions(tableName, startDate, endDate);
    }

    public ResultSet query(String queryStr) throws IuniDAImpalaException {
        if (connector == null) {
            String errorStr = "Connector is null.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        if (!connector.isConnected()) {
            String errorStr = "hive can not connected, please check impala status.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        ResultSet rs = null;
        try {
            Statement stmt = this.connector.getConn().createStatement();
            rs = stmt.executeQuery(queryStr);
//            stmt.close();
        } catch (SQLException e) {
            String errorStr = "hive execute error, please check sql : " + queryStr + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        return rs;
    }

}
