package com.iuni.data.analyze;

import com.iuni.data.analyze.cube.DataCube;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.TType;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.webkpi.WebKpiByChannel;
import com.iuni.data.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * analyze whole site pv, uv, ip etc group by channel
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class WholeSiteAnalyzeByChannel extends PageAnalyze {

    private static final Logger logger = LoggerFactory.getLogger(WholeSiteAnalyzeByChannel.class);

    public WholeSiteAnalyzeByChannel() {
        super();
    }

    @Override
    public void start() {
        logger.info("WholeSiteAnalyzeByChannel starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("WholeSiteAnalyzeByChannel stopping");
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
            result.addWebKpiByChannelMap(analyze(tType, entry.getKey(), entry.getKey().getTime(), entry.getValue().getTime()));
            DataCube.addResult(result);
        }
    }

    /**
     * use impala or hive to analyze whole site pv/uv/ip/vv/stayTime/jump by channel
     *
     * @param time           the time of partition
     * @param startTimeStamp the start time of analyze
     * @param endTimeStamp   the end time of analyze
     * @param tType          the time type of analyze
     * @return pageWebKpiForWholeSiteMap
     */
    private Map<String, WebKpiByChannel> analyze(TType tType, Date time, long startTimeStamp, long endTimeStamp) {
        Map<String, WebKpiByChannel> webKpiByChannelMap = new HashMap<>();
        analyzePv(tType, time, startTimeStamp, endTimeStamp, webKpiByChannelMap);
        analyzeUv(tType, time, startTimeStamp, endTimeStamp, webKpiByChannelMap);
        analyzeIp(tType, time, startTimeStamp, endTimeStamp, webKpiByChannelMap);
        analyzeVv(tType, time, startTimeStamp, endTimeStamp, webKpiByChannelMap);
        return webKpiByChannelMap;
    }

    /**
     * analyze pv
     *
     * @param tType              the time type of analyze
     * @param time               the time of partition
     * @param startTimeStamp     the start time of analyze
     * @param endTimeStamp       the end time of analyze
     * @param webKpiByChannelMap result of analyze
     */
    public void analyzePv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpiByChannel> webKpiByChannelMap) {
        logger.info("analyze whole site pv by channel of {}:{} started", tType, time);
        // concatenate the sql to query pv
        String sqlStr = "select adId, count(1) from iunilog where 1 = 1 " + super.transTimeCondition(time, startTimeStamp, endTimeStamp) + " group by adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String adId = StringUtils.isBlank(result.get(0).trim()) ? "" : result.get(0).trim();
            Channel channel = DataCube.findChannelByCode(adId);
            String key = channel.getCode();
            WebKpiByChannel webKpiByChannel = webKpiByChannelMap.get(key);
            if (webKpiByChannel == null) {
                webKpiByChannel = new WebKpiByChannel(time, tType.getPattern(), channel, new Date());
                webKpiByChannelMap.put(key, webKpiByChannel);
            }
            long pv = Long.parseLong(result.get(1) == null ? "0" : result.get(1));
            webKpiByChannel.setPv(webKpiByChannel.getPv() + pv);
        }
        logger.info("analyze whole site pv by channel of {}:{} finished.", tType, time);
    }

    /**
     * analyze uv
     *
     * @param tType              the time type of analyze
     * @param time               the time of partition
     * @param startTimeStamp     the start time of analyze
     * @param endTimeStamp       the end time of analyze
     * @param webKpiByChannelMap result of analyze
     */
    public void analyzeUv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpiByChannel> webKpiByChannelMap) {
        logger.info("analyze whole site uv by channel of {}:{} started", tType, time);
        // concatenate the sql to query uv
        String sqlStr = "select adId, count(distinct vk) from iunilog where 1 = 1 " + super.transTimeCondition(time, startTimeStamp, endTimeStamp) + " group by adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String adId = StringUtils.isBlank(result.get(0).trim()) ? "" : result.get(0).trim();
            Channel channel = DataCube.findChannelByCode(adId);
            String key = channel.getCode();
            WebKpiByChannel webKpiByChannel = webKpiByChannelMap.get(key);
            if (webKpiByChannel == null) {
                webKpiByChannel = new WebKpiByChannel(time, tType.getPattern(), channel, new Date());
                webKpiByChannelMap.put(key, webKpiByChannel);
            }
            long uv = Long.parseLong(result.get(1) == null ? "0" : result.get(1));
            webKpiByChannel.setUv(webKpiByChannel.getUv() + uv);
        }
        logger.info("analyze whole site uv by channel of {}:{} finished.", tType, time);
    }

    /**
     * analyze ip
     *
     * @param tType              the time type of analyze
     * @param time               the time of partition
     * @param startTimeStamp     the start time of analyze
     * @param endTimeStamp       the end time of analyze
     * @param webKpiByChannelMap result of analyze
     */
    public void analyzeIp(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpiByChannel> webKpiByChannelMap) {
        logger.info("analyze whole site ip by channel of {}:{} started", tType, time);
        // concatenate the sql to query unique ip
        String sqlStr = "select adId, count(distinct remote_addr) from iunilog where 1 = 1 " + super.transTimeCondition(time, startTimeStamp, endTimeStamp) + " group by adId";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String adId = StringUtils.isBlank(result.get(0).trim()) ? "" : result.get(0).trim();
            Channel channel = DataCube.findChannelByCode(adId);
            String key = channel.getCode();
            WebKpiByChannel webKpiByChannel = webKpiByChannelMap.get(key);
            if (webKpiByChannel == null) {
                webKpiByChannel = new WebKpiByChannel(time, tType.getPattern(), channel, new Date());
                webKpiByChannelMap.put(key, webKpiByChannel);
            }
            long ip = Long.parseLong(result.get(1) == null ? "0" : result.get(1));
            webKpiByChannel.setIp(webKpiByChannel.getIp() + ip);
        }
        logger.info("analyze whole site ip by channel of {}:{} finished.", tType, time);
    }

    /**
     * analyze vv, jump times and stay time of every session
     *
     * @param tType              the time type of analyze
     * @param time               the time of partition
     * @param startTimeStamp     the start time of analyze
     * @param endTimeStamp       the end time of analyze
     * @param webKpiByChannelMap result of analyze
     */
    public void analyzeVv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, WebKpiByChannel> webKpiByChannelMap) {
        logger.info("analyze whole site vv/jumpTimes/stayTime by channel of {}:{} started", tType, time);
        // concatenate the sql to query visit views
        String sqlStr = "select adId, vk, sid, count(1), max(`timestamp`) - min(`timestamp`) from iunilog where 1 = 1 "
                + super.transTimeCondition(time, startTimeStamp, endTimeStamp)
                + " group by adId, vk, sid";

        List<List<String>> resultList = impalaOperator.query(sqlStr);
        for (List<String> result : resultList) {
            String adId = StringUtils.isBlank(result.get(0).trim()) ? "" : result.get(0).trim();
            Channel channel = DataCube.findChannelByCode(adId);
//            String vk = StringUtils.isBlank(result.get(1).trim()) ? "" : result.get(1).trim();
//            String sid = StringUtils.isBlank(result.get(2).trim()) ? "" : result.get(2).trim();

            String key = channel.getCode();
            WebKpiByChannel webKpiByChannel = webKpiByChannelMap.get(key);
            if (webKpiByChannel == null) {
                webKpiByChannel = new WebKpiByChannel(time, tType.getPattern(), channel, new Date());
                webKpiByChannelMap.put(key, webKpiByChannel);
            }
            // the 3th is the page views of every session
            long views = Long.parseLong(result.get(3) == null ? "0" : result.get(3));
            // if the session just have one view, the number of jump add one
            if (views == 1)
                webKpiByChannel.setTotalJump(webKpiByChannel.getTotalJump() + 1);
            // VV + 1
            webKpiByChannel.setVv(webKpiByChannel.getVv() + 1);
            // the 4th is the stay time of every session.
            long sTime = Long.parseLong(result.get(4) == null ? "0" : result.get(4));
            webKpiByChannel.setStayTime(webKpiByChannel.getStayTime() + sTime);
        }
        logger.info("analyze whole site vv/jumpTimes/stayTime by channel of {}:{} finished.", tType, time);
    }

}
