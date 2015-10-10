package com.iuni.data.impala;

import com.iuni.data.exceptions.IuniDAImpalaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * impala连接器
 */
public class ImpalaConnector {
    private static Logger logger = LoggerFactory.getLogger(ImpalaConnector.class);

    private final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    private String url;
    private String driver;

    private ImpalaConnector() {
    }

    public ImpalaConnector(String url) throws IuniDAImpalaException {
        this.url = url;
        this.driver = JDBC_DRIVER_NAME;
    }

    public ImpalaConnector(String url, String driver) throws IuniDAImpalaException {
        this.url = url;
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection connect() throws IuniDAImpalaException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url.trim());
        } catch (ClassNotFoundException e) {
            String errorStr = "HiveDriver not found, driver class is: " + driver + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        } catch (SQLException e) {
            String errorStr = "impala connection error, url is: " + url + ". error msg : " + e.getMessage();
            logger.error(errorStr);
            throw new IuniDAImpalaException(errorStr);
        }
    }

}
