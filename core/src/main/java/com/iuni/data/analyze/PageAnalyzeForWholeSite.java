package com.iuni.data.analyze;

import com.iuni.data.analyze.cube.DataCube;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.Constants;
import com.iuni.data.common.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.persist.domain.config.*;
import com.iuni.data.persist.domain.webkpi.WebKpi;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * analyze whole site page pv, uv, ip eg.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PageAnalyzeForWholeSite extends PageAnalyze {

    private static final Logger logger = LoggerFactory.getLogger(PageAnalyzeForWholeSite.class);

    public PageAnalyzeForWholeSite() {
        super();
    }

    @Override
    public void start() {
        logger.info("PageAnalyzeForWholeSite starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("PageAnalyzeForWholeSite stopping");
        super.stop();
    }

    /**
     * timeStr is like 20150101000000
     *
     * @param startTime       the start time of analyze
     * @param endTime         the end time of analyze
     * @param tType           the time type of analyze, day/hour/minute eg.
     * @param createPartition need setBasicInfoForCreate partition or not
     */
    @Override
    public void analyze(Date startTime, Date endTime, TType tType, boolean createPartition) {
        super.init(startTime, endTime, createPartition);

        Map<Date, Date> timeRangeMap = DateUtils.parseTimeRange(startTime, endTime, tType);
        for (Map.Entry<Date, Date> entry : timeRangeMap.entrySet()) {
            Result result = new Result();
            result.settType(tType);
            result.setTime(entry.getKey());
            result.addPageWebKpiForWholeSiteMap(analyze(tType, entry.getKey(), entry.getKey().getTime(), entry.getValue().getTime()));
            DataCube.addResult(result);
        }
    }

    /**
     * use impala or hive to analyze page pv and uv
     *
     * @param time           the time of partition
     * @param startTimeStamp the start time of analyze
     * @param endTimeStamp   the end time of analyze
     * @param tType          the time type of analyze
     * @return pageWebKpiForWholeSiteMap
     */
    private Map<String, WebKpi> analyze(TType tType, Date time, long startTimeStamp, long endTimeStamp) {
        Map<String, WebKpi> pageWebKpiForWholeSiteMap = new HashMap<>();
        analyzePv(tType, time, startTimeStamp, endTimeStamp, pageWebKpiForWholeSiteMap);
        analyzeUv(tType, time, startTimeStamp, endTimeStamp, pageWebKpiForWholeSiteMap);
        analyzeIp(tType, time, startTimeStamp, endTimeStamp, pageWebKpiForWholeSiteMap);
        analyzeNewUv(tType, time, startTimeStamp, endTimeStamp, pageWebKpiForWholeSiteMap);
        analyzeVv(tType, time, startTimeStamp, endTimeStamp, pageWebKpiForWholeSiteMap);
        return pageWebKpiForWholeSiteMap;
    }

    /**
     * analyze pv, sum request time and sum bytes
     *
     * @param tType                     the time type of analyze
     * @param time                      the time of partition
     * @param startTimeStamp            the start time of analyze
     * @param endTimeStamp              the end time of analyze
     * @param pageWebKpiForWholeSiteMap result of analyze
     */
    public void analyzePv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpi> pageWebKpiForWholeSiteMap) {
        logger.info("analyze page pv/requestTime/bodyBytes of {}:{} started", tType, time);
        // concatenate the sql to query pv
        String sqlStr = "select country, area, region, city, couty, isp, realReferer, adId, count(*), sum(request_time), sum(body_bytes_sent) from" +
                " (" + selectRealReferer(time, startTimeStamp, endTimeStamp) + ")t" +
                " group by country, area, region, city, couty, isp, realReferer, adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String country = StringUtils.isBlank(result.get(0)) ? Constants.default_country : result.get(0);
            String area = result.get(1);
            String province = result.get(2);
            String city = result.get(3);
            String county = result.get(4);
            String isp = result.get(5);

            String source = result.get(6);
            FlowSource flowSource = DataCube.findSourceByUrl(source);

            String adId = result.get(7);
            Channel channel = DataCube.findChannelByCode(adId);

            String key = country + area + province + city + county + isp + flowSource.getId() + channel.getId();
            WebKpi webKpi = pageWebKpiForWholeSiteMap.get(key);
            if (webKpi == null) {
                webKpi = new WebKpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                pageWebKpiForWholeSiteMap.put(key, webKpi);
            }
            int pv = Integer.parseInt(result.get(8));
            webKpi.setPv(webKpi.getPv() + pv);
            // transfer totalTime's unit to second
            long totalTime = (long) (Float.parseFloat(result.get(9)) * 1000);
            webKpi.setTotalTime(webKpi.getTotalTime() + totalTime);
            long totalSize = Long.parseLong(result.get(10));
            webKpi.setTotalSize(webKpi.getTotalSize() + totalSize);
        }
        logger.info("analyze page pv/requestTime/bodyBytes of {}:{} finished.", tType, time);
    }

    /**
     * analyze uv
     *
     * @param tType                     the time type of analyze
     * @param time                      the time of partition
     * @param startTimeStamp            the start time of analyze
     * @param endTimeStamp              the end time of analyze
     * @param pageWebKpiForWholeSiteMap result of analyze
     */
    public void analyzeUv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpi> pageWebKpiForWholeSiteMap) {
        logger.info("analyze page uv of {}:{} started", tType, time);
        // concatenate the sql to query uv
        String sqlStr = "select country, area, region, city, couty, isp, realReferer, adId, count(distinct cookie_vk) from" +
                " (" + selectRealReferer(time, startTimeStamp, endTimeStamp) + ")t" +
                " group by country, area, region, city, couty, isp, realReferer, adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String country = StringUtils.isBlank(result.get(0)) ? Constants.default_country : result.get(0);
            String area = result.get(1);
            String province = result.get(2);
            String city = result.get(3);
            String county = result.get(4);
            String isp = result.get(5);

            String source = result.get(6);
            FlowSource flowSource = DataCube.findSourceByUrl(source);

            String adId = result.get(7);
            Channel channel = DataCube.findChannelByCode(adId);

            String key = country + area + province + city + county + isp + flowSource.getId() + channel.getId();
            WebKpi webKpi = pageWebKpiForWholeSiteMap.get(key);
            if (webKpi == null) {
                webKpi = new WebKpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                pageWebKpiForWholeSiteMap.put(key, webKpi);
            }
            // the 8th column is uv
            int uv = Integer.parseInt(result.get(8));
            webKpi.setUv(webKpi.getUv() + uv);
        }
        logger.info("analyze page uv of {}:{} finished.", tType, time);
    }

    /**
     * analyze ip
     *
     * @param tType                     the time type of analyze
     * @param time                      the time of partition
     * @param startTimeStamp            the start time of analyze
     * @param endTimeStamp              the end time of analyze
     * @param pageWebKpiForWholeSiteMap result of analyze
     */
    public void analyzeIp(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpi> pageWebKpiForWholeSiteMap) {
        logger.info("analyze page ip of {}:{} started", tType, time);
        // concatenate the sql to query unique ip
        String sqlStr = "select country, area, region, city, couty, isp, realReferer, adId, count(distinct remote_addr) from " +
                " (" + selectRealReferer(time, startTimeStamp, endTimeStamp) + ")t " +
                " group by country, area, region, city, couty, isp, realReferer, adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String country = StringUtils.isBlank(result.get(0)) ? Constants.default_country : result.get(0);
            String area = result.get(1);
            String province = result.get(2);
            String city = result.get(3);
            String county = result.get(4);
            String isp = result.get(5);

            String source = result.get(6);
            FlowSource flowSource = DataCube.findSourceByUrl(source);

            String adId = result.get(7);
            Channel channel = DataCube.findChannelByCode(adId);

            String key = country + area + province + city + county + isp + flowSource.getId() + channel.getId();
            WebKpi webKpi = pageWebKpiForWholeSiteMap.get(key);
            if (webKpi == null) {
                webKpi = new WebKpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                pageWebKpiForWholeSiteMap.put(key, webKpi);
            }
            int ip = Integer.parseInt(result.get(8));
            webKpi.setIp(webKpi.getIp() + ip);
        }
        logger.info("analyze page ip of {}:{} finished.", tType, time);
    }

    /**
     * analyze new uv
     *
     * @param tType                     the time type of analyze
     * @param time                      the time of partition
     * @param startTimeStamp            the start time of analyze
     * @param endTimeStamp              the end time of analyze
     * @param pageWebKpiForWholeSiteMap result of analyze
     */
    public void analyzeNewUv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpi> pageWebKpiForWholeSiteMap) {
        logger.info("analyze page newUv of {}:{} started", tType, time);
        // concatenate the sql to query old uv
        String sqlStr = "select country, area, region, city, couty, isp, realReferer, adId, count(distinct cookie_vk) from" +
                " (select country, area, region, city, couty, isp, realReferer, adId, TT2.cookie_vk from" +
                " (" + selectRealReferer(time, startTimeStamp, endTimeStamp) + ") TT1," +
                " (select distinct(cookie_vk) from " + logTableName + " where `timestamp` < " + startTimeStamp + ") TT2" +
                " where TT1.cookie_vk = TT2.cookie_vk) NEWUV" +
                " group by country, area, region, city, couty, isp, realReferer, adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String country = StringUtils.isBlank(result.get(0)) ? Constants.default_country : result.get(0);
            String area = result.get(1);
            String province = result.get(2);
            String city = result.get(3);
            String county = result.get(4);
            String isp = result.get(5);

            String source = result.get(6);
            FlowSource flowSource = DataCube.findSourceByUrl(source);

            String adId = result.get(7);
            Channel channel = DataCube.findChannelByCode(adId);

            String key = country + area + province + city + county + isp + flowSource.getId() + channel.getId();
            WebKpi webKpi = pageWebKpiForWholeSiteMap.get(key);
            if (webKpi == null) {
                webKpi = new WebKpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                pageWebKpiForWholeSiteMap.put(key, webKpi);
            }
            int newUv = Integer.parseInt(result.get(8));
            webKpi.setNewUv(webKpi.getNewUv() + newUv);
        }
        logger.info("analyze page newUv of {}:{} finished.", tType, time);
    }

    /**
     * analyze vv, jump times and stay time of every session
     *
     * @param tType                     the time type of analyze
     * @param time                      the time of partition
     * @param startTimeStamp            the start time of analyze
     * @param endTimeStamp              the end time of analyze
     * @param pageWebKpiForWholeSiteMap result of analyze
     */
    public void analyzeVv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpi> pageWebKpiForWholeSiteMap) {
        logger.info("analyze page vv/jumpTimes/stayTime of {}:{} started", tType, time);
        // concatenate the sql to query visit views
        String sqlStr = "select country, area, region, city, couty, isp, realReferer, adId, count(*), max(`timestamp`) - min(`timestamp`) from " +
                "(" + selectRealReferer(time, startTimeStamp, endTimeStamp) + ") t " +
                "group by country, area, region, city, couty, isp, cookie_sessionid, realReferer, adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String country = StringUtils.isBlank(result.get(0)) ? Constants.default_country : result.get(0);
            String area = result.get(1);
            String province = result.get(2);
            String city = result.get(3);
            String county = result.get(4);
            String isp = result.get(5);

            String source = result.get(6);
            FlowSource flowSource = DataCube.findSourceByUrl(source);

            String adId = result.get(7);
            Channel channel = DataCube.findChannelByCode(adId);

            String key = country + area + province + city + county + isp + flowSource.getId() + channel.getId();
            WebKpi webKpi = pageWebKpiForWholeSiteMap.get(key);
            if (webKpi == null) {
                webKpi = new WebKpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                pageWebKpiForWholeSiteMap.put(key, webKpi);
            }
            // 7th is every sid's visit views
            int vTimes = Integer.parseInt(result.get(8));
            // if the session just have one visit, the number of jump add one
            if (vTimes == 1)
                webKpi.setTotalJump(webKpi.getTotalJump() + 1);
            // VV + 1
            webKpi.setVv(webKpi.getVv() + 1);
            // 8th is this session's stay time.
            int sTime = (int) (Long.parseLong(result.get(9)) / 1000);
            webKpi.setStayTime(webKpi.getStayTime() + sTime);
        }
        logger.info("analyze page vv/jumpTimes/stayTime of {}:{} finished.", tType, time);
    }

    /**
     * First of all find out the real referer of all visits in every session,
     * then use the referer of the first visit of every session as the real referer.
     *
     * @param time           the time of partition
     * @param startTimeStamp the start timestamp of analyze
     * @param endTimeStamp   the end timestamp of analyze
     * @return realReferer sql
     */
    private String selectRealReferer(Date time, long startTimeStamp, long endTimeStamp) {
        return "select t1.realReferer , t2.* from " + "(select cookie_sessionid, max(referer) as realReferer from " +
                logTableName + " where connection_requests = 1 " + super.transTimeCondition(time, startTimeStamp, endTimeStamp) +
                " group by cookie_sessionid) t1, " + logTableName + " t2 where t1.cookie_sessionid = t2.cookie_sessionid" +
                super.transTimeCondition(time, startTimeStamp, endTimeStamp);
    }

}
