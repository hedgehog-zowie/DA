package com.iuni.data.impala;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iuni.data.exceptions.IuniDAImpalaException;

public class ImpalaOperator {
    private static Logger logger = LoggerFactory.getLogger(ImpalaOperator.class);

    private ImpalaConnector connector;

//    private Lock opeLock = new ReentrantLock();

    private ImpalaOperator() {
    }

    public ImpalaOperator(ImpalaConnector connector) throws IuniDAImpalaException {
        this.connector = connector;
        if (!this.connector.isConnected())
            this.connector.reconnect();
    }

    /**
     * invalidate impala metadata with hive
     *
     * @return
     * @throws IuniDAImpalaException
     */
    public synchronized boolean invalidateMetadata() throws IuniDAImpalaException {
        boolean result;
        if (connector == null) {
            String errorStr = "Connector is null.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        if (!connector.isConnected()) {
            String errorStr = "impala can not connected, please check impala status.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        Statement stmt = null;
//        opeLock.lock();
        try {
            String sqlStr = "invalidate metadata";
            stmt = this.connector.getConn().createStatement();
            result = stmt.execute(sqlStr);
            stmt.close();
            logger.info("invalidate metadata");
        } catch (SQLException e) {
            String errorStr = "invalidate metadata error. error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.info("close impala stmt error,", e);
                }
//            opeLock.unlock();
        }
        return result;
    }

    public List<List<String>> query(String queryStr) throws IuniDAImpalaException {
        if (connector == null) {
            String errorStr = "Connector is null.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        if (!connector.isConnected()) {
//            String errorStr = "impala can not connected, please check impala status.";
//            logger.error(errorStr);
//            throw new IuniDAImpalaException(errorStr);
            connector.reconnect();
            logger.info("impala not connected, re-connect.");
        }
        List<List<String>> resultList = new ArrayList<>();
        ResultSet rs = null;
        Statement stmt = null;
//        opeLock.lock();
        try {
            stmt = this.connector.getConn().createStatement();
            rs = stmt.executeQuery(queryStr);
            int columnSize = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 0; i < columnSize; i++)
                    row.add(rs.getString(i + 1));
                resultList.add(row);
            }
        } catch (SQLException e) {
            String errorStr = "impala execute error, please check sql : " + queryStr + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.info("close impala rs error,", e);
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.info("close impala stmt error,", e);
                }
//            opeLock.unlock();
        }
        return resultList;
    }

}
