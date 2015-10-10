package com.iuni.data.hive;

import com.iuni.data.exceptions.IuniDAImpalaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * HIVE连接器
 */
public class HiveConnector {
    private static Logger logger = LoggerFactory.getLogger(HiveConnector.class);

    private final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    private String url;
    private String driver;
    private String user;
    private String password;

    public HiveConnector() {
    }

    public HiveConnector(String url, String user, String password) throws IuniDAImpalaException {
        this.url = url;
        this.driver = JDBC_DRIVER_NAME;
        this.user = user;
        this.password = password;
    }

    public HiveConnector(String url, String driver, String user, String password) throws IuniDAImpalaException {
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
    }

    public Connection connect() throws IuniDAImpalaException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url.trim(), user, password);
        } catch (ClassNotFoundException e) {
            String errorStr = "HiveDriver not found, driver class is: " + driver + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        } catch (SQLException e) {
            String errorStr = "connect to hive error, url is: " + url + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
