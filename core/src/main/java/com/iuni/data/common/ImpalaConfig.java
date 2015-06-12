package com.iuni.data.common;

import com.iuni.data.exceptions.IuniDAConfigureException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpalaConfig {

    private static Logger logger = LoggerFactory.getLogger(ImpalaConfig.class);

    private String driver;
    private String url;
    private String hiveUrl;
    private String hiveUser;
    private String hivePassword;

    public ImpalaConfig(String filePath) throws Exception {
        config(filePath);
    }

    public void reConfig(String filePath) throws Exception {
        config(filePath);
    }

    private void config(String filePath) throws Exception {
        PropertiesConfiguration config = new PropertiesConfiguration(filePath);
        driver = config.getString("impala.driver");
        url = config.getString("impala.url");
        hiveUrl = config.getString("hive.url");
        hiveUser = config.getString("hive.user");
        hivePassword = config.getString("hive.password");
        if (StringUtils.isBlank(driver) || StringUtils.isBlank(url) || StringUtils.isBlank(hiveUrl)) {
            String errorStr = "config impala property error, please check config file [ " + filePath + " ].";
            logger.error(errorStr);
            throw new IuniDAConfigureException(errorStr);
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHiveUrl() {
        return hiveUrl;
    }

    public void setHiveUrl(String hiveUrl) {
        this.hiveUrl = hiveUrl;
    }

    public String getHiveUser() {
        return hiveUser;
    }

    public void setHiveUser(String hiveUser) {
        this.hiveUser = hiveUser;
    }

    public String getHivePassword() {
        return hivePassword;
    }

    public void setHivePassword(String hivePassword) {
        this.hivePassword = hivePassword;
    }
}
