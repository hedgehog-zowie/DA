package com.iuni.data.hive;

import com.iuni.data.exceptions.IuniDADateFormatException;
import com.iuni.data.exceptions.IuniDAHiveException;
import com.iuni.data.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 操作HIVE
 */
public class HiveOperator {
    private static Logger logger = LoggerFactory.getLogger(HiveOperator.class);

    private HiveConnector connector;

    private HiveOperator() {
    }

    public HiveOperator(HiveConnector connector) {
        this.connector = connector;
    }

    public void addJar(String jarPath) {
        if (connector == null) {
            String errorStr = "hive connector is null.";
            logger.error(errorStr);
            throw new IuniDAHiveException(errorStr);
        }

        Connection connection = null;
        try {
            connection = this.connector.connect();
            logger.info("create new hive connection");
            Statement stmt = connection.createStatement();
            stmt.execute("add jar " + jarPath);
            stmt.close();
            logger.info("added jar: {}", jarPath);
        } catch (SQLException e) {
            logger.error("add jar error, addCmd: {}", jarPath);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close hive connection error,", e);
                }
                logger.info("closed hive connection");
            }
        }
    }

    /**
     * 添加分区信息，时间格式为：yyyyMMddHHmmss
     *
     * @param timeStr
     * @throws IuniDADateFormatException
     */
    public synchronized void parseAndAddPartition(String tableName, String timeStr) {
        if (connector == null) {
            String errorStr = "hive connector is null.";
            logger.error(errorStr);
            throw new IuniDAHiveException(errorStr);
        }

        String addPartitionStr = "ALTER TABLE %s ADD IF NOT EXISTS PARTITION (time = %s)";
        addPartitionStr = String.format(addPartitionStr, tableName, timeStr);
        Connection connection = null;
        try {
            connection = this.connector.connect();
            logger.info("create new hive connection");
            Statement stmt = connection.createStatement();
            stmt.execute(addPartitionStr);
            stmt.close();
            logger.info("added partition: {}", timeStr);
        } catch (SQLException e) {
            logger.error("add partition error, partition time is: {}.", timeStr, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close hive connection error,", e);
                }
                logger.info("closed hive connection");
            }
        }
    }

    /**
     * 添加分区信息
     *
     * @param date
     */
    public void parseAndAddPartition(String tableName, Date date) {
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
    public void parseAndAddPartitions(String tableName, Date startDate, Date endDate) {
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
    public void parseAndAddPartitions(String tableName, String startTime, String endTime) {
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
    public void parseAndDelPartition(String tableName, String timeStr) {
        if (connector == null) {
            String errorStr = "hive connector is null.";
            logger.error(errorStr);
            throw new IuniDAHiveException(errorStr);
        }

        String dropPartitionStr = "ALTER TABLE %s DROP IF EXISTS PARTITION (time = %s)";
        dropPartitionStr = String.format(dropPartitionStr, tableName, timeStr);
        Connection connection = null;
        try {
            connection = this.connector.connect();
            logger.info("create new hive connection");
            Statement stmt = connection.createStatement();
            stmt.execute(dropPartitionStr);
            stmt.close();
            logger.info("dropped partition: {}", timeStr);
        } catch (SQLException e) {
            logger.error("drop partition error, partition time is: {}, err msg: {}", timeStr, e.getLocalizedMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close hive connection error,", e);
                }
                logger.info("closed hive connection");
            }
        }
    }

    /**
     * drop partition
     *
     * @param tableName
     * @param parDate
     * @throws IuniDADateFormatException
     */
    public void parseAndDelPartition(String tableName, Date parDate) {
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
    public void parseAndDelPartitions(String tableName, Date startDate, Date endDate) {
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
    public void parseAndDelPartitions(String tableName, String startTime, String endTime) {
        // delete partition every minute
        Date startDate = DateUtils.simpleDateStrToDate(startTime);
        Date endDate = DateUtils.simpleDateStrToDate(endTime);
        parseAndDelPartitions(tableName, startDate, endDate);
    }

    /**
     * query data
     *
     * @param queryStr
     * @return
     * @throws IuniDAHiveException
     */
    public List<List<String>> query(String queryStr) {
        if (connector == null) {
            String errorStr = "Connector is null.";
            logger.error(errorStr);
            throw new IuniDAHiveException(errorStr);
        }

        List<List<String>> resultList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = this.connector.connect();
            logger.info("create new hive connection");
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryStr);
            int columnSize = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 0; i < columnSize; i++)
                    row.add(rs.getString(i + 1));
                resultList.add(row);
            }
            logger.info("executed query: {}", queryStr);
        } catch (SQLException e) {
            String errorStr = "hive execute error, please check sql : " + queryStr + ". error msg : " + e.getMessage();
            logger.error(errorStr, e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("close hive rs error,", e);
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error("close hive stmt error,", e);
                }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close hive connection error,", e);
                }
                logger.info("closed hive connection");
            }
        }
        return resultList;
    }

    /**
     * insert data
     *
     * @param insertSql
     * @return
     */
    public boolean insert(String insertSql) {
        if (connector == null) {
            String errorStr = "hive connector is null.";
            logger.error(errorStr);
            throw new IuniDAHiveException(errorStr);
        }

        Connection connection = null;
        try {
            connection = this.connector.connect();
            logger.info("create new hive connection");
            Statement stmt = connection.createStatement();
            return stmt.execute(insertSql);
        } catch (SQLException e) {
            String errorStr = "hive execute error, please check sql : " + insertSql + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAHiveException(errorStr);
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close hive connection error,", e);
                }
            logger.info("closed hive connection");
        }
    }

}
