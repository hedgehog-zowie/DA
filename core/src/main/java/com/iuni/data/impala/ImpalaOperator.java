package com.iuni.data.impala;

import com.iuni.data.exceptions.IuniDAImpalaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImpalaOperator {
    private static Logger logger = LoggerFactory.getLogger(ImpalaOperator.class);

    private ImpalaConnector connector;

    private ImpalaOperator() {
    }

    public ImpalaOperator(ImpalaConnector connector){
        this.connector = connector;
    }

    /**
     * invalidate impala metadata with hive
     *
     * @return
     * @throws IuniDAImpalaException
     */
    public synchronized void invalidateMetadata(){
        if (connector == null) {
            String errorStr = "Connector is null.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }

        String sqlStr = "invalidate metadata";
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.connector.connect();
            logger.info("create new impala connection");
            stmt = connection.createStatement();
            stmt.execute(sqlStr);
            stmt.close();
            logger.info("invalidated metadata");
        } catch (SQLException e) {
            logger.error("invalidate metadata error.", e);
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.info("close impala stmt error,", e);
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close impala connection error,", e);
                }
            logger.info("closed impala connection");
        }
    }

    public List<List<String>> query(String queryStr) {
        if (connector == null) {
            String errorStr = "Connector is null.";
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }

        List<List<String>> resultList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = this.connector.connect();
            logger.info("create new impala connection");
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
            String errorStr = "impala execute error, please check sql : " + queryStr + ". error msg : " + e.getMessage();
            logger.error(errorStr, e);
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("close impala connection error,", e);
                }
                logger.info("closed impala connection");
            }
        }
        return resultList;
    }

}
