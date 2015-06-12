package com.iuni.data.impala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iuni.data.exceptions.IuniDAImpalaException;

public class ImpalaConnector {
    private static Logger logger = LoggerFactory.getLogger(ImpalaConnector.class);

    private final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    private String url;
    private String driver;
    private Connection conn;

    private ImpalaConnector() {
    }

    public ImpalaConnector(String url) throws IuniDAImpalaException {
        this.url = url;
        this.driver = JDBC_DRIVER_NAME;
        this.connect();
    }

    public ImpalaConnector(String url, String driver) throws IuniDAImpalaException {
        this.url = url;
        this.driver = driver;
        this.connect();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection getConn() {
        return conn;
    }

    private boolean connect() throws IuniDAImpalaException {
        if (this.isConnected()) {
            logger.info("impala connection pool has inited.");
            return true;
        }
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url.trim());
        } catch (ClassNotFoundException e) {
            String errorStr = "HiveDriver not found, driver class is: " + driver + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        } catch (SQLException e) {
            String errorStr = "impala connection error, url is: " + url + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        return true;
    }

    public boolean isConnected() throws IuniDAImpalaException {
        if (conn == null)
            return false;
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            String errorStr = "check impala connect status error, conn is: " + conn.toString() + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
    }

    public boolean reconnect() throws IuniDAImpalaException {
        this.close();
        return connect();
    }

    public boolean close() throws IuniDAImpalaException {
        if (conn == null || !this.isConnected())
            return true;
        try {
            conn.close();
            return conn.isClosed();
        } catch (SQLException e) {
            String errorStr = "close impala connection error, conn is: " + conn.toString() + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
    }
}
