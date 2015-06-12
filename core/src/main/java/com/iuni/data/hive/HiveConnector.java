package com.iuni.data.hive;

import com.iuni.data.exceptions.IuniDAImpalaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 连接器，提供连接、重连HIVE功能
 */
public class HiveConnector {
    private static Logger logger = LoggerFactory.getLogger(HiveConnector.class);

    private final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    private String url;
    private String driver;
    private String user;
    private String password;
    private Connection conn;

    public HiveConnector(String url, String user, String password) throws IuniDAImpalaException {
        this.url = url;
        this.driver = JDBC_DRIVER_NAME;
        this.user = user;
        this.password = password;
        this.connect();
    }

    public HiveConnector(String url, String driver, String user, String password) throws IuniDAImpalaException {
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
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
            logger.info("hive has been connected.");
            return true;
        }
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url.trim(), user, password);
        } catch (ClassNotFoundException e) {
            String errorStr = "HiveDriver not found, driver class is: " + driver + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        } catch (SQLException e) {
            String errorStr = "connect to hive error, url is: " + url + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
        return true;
    }

    public boolean reconnect() throws IuniDAImpalaException {
        this.close();
        return connect();
    }

    public boolean isConnected() throws IuniDAImpalaException {
        if (conn == null)
            return false;
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            String errorStr = "check hive connect status error, conn is: " + conn.toString() + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
    }

    public boolean close() throws IuniDAImpalaException {
        if (conn == null || !this.isConnected())
            return true;
        try {
            conn.close();
            return conn.isClosed();
        } catch (SQLException e) {
            String errorStr = "close hive connection error, conn is: " + conn.toString() + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
    }
}
