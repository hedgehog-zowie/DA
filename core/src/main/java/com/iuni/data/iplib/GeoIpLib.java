package com.iuni.data.iplib;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.iuni.data.Context;
import com.iuni.data.conf.Configurable;
import com.iuni.data.exceptions.IuniDAIpException;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.IspResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class GeoIpLib extends AbstractIpLib {

    private static final Logger logger = LoggerFactory.getLogger(GeoIpLib.class);

    private DatabaseReader reader;

    public GeoIpLib() {
        super();
    }

    @Override
    public void configure(Context context) {
        String libFilePath = context.getString("lib");
        Preconditions.checkState(libFilePath != null, "The parameter lib must be specified");
        File file = new File(libFilePath);
        try {
            reader = new DatabaseReader.Builder(file).build();
        } catch (IOException e) {
            String errorStr = new StringBuilder("GeoIpLib config failed, creates the DatabaseReader object error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
    }

    @Override
    public void start() {
        logger.info("GeoIpLib starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("GeoIpLib stop");
        super.stop();
    }

    @Override
    @Deprecated
    public void getIpInfo(IpInfo ipInfo) {
        logger.info("get ip info of {}", ipInfo.getIp());
    }

    @Override
    public IpInfo getIpInfo(String ipStr) {
        logger.debug("get ip info of {}", ipStr);
        ipStr = ipStr.trim();
        IpInfo ipInfo = new IpInfo();
        ipInfo.setIp(ipStr);
        try {
            InetAddress ipAddress = InetAddress.getByName(ipStr);
            CityResponse cityResponse = reader.city(ipAddress);
            Country country = cityResponse.getCountry();
            ipInfo.setCountry(country.getNames().get("zh-CN"));
            Subdivision subdivision = cityResponse.getMostSpecificSubdivision();
            ipInfo.setRegion(subdivision.getNames().get("zh-CN") == null ? subdivision.getName() : subdivision.getNames().get("zh-CN"));
            City city = cityResponse.getCity();
            ipInfo.setCity(city.getNames().get("zh-CN"));
        } catch (UnknownHostException e) {
            String errorStr = new StringBuilder("GeoIpLib getIpInfo failed, trans ipStr to InetAddress error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
        } catch (GeoIp2Exception e) {
            String errorStr = new StringBuilder("GeoIpLib getIpInfo failed, error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
        } catch (Exception e) {
            String errorStr = new StringBuilder("GeoIpLib getIpInfo failed, error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
        }

        return ipInfo;
    }

}
