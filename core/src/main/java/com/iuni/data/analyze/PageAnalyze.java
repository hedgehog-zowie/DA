package com.iuni.data.analyze;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.analyze.cube.DataCube;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.Constants;
import com.iuni.data.common.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.common.UrlUtils;
import com.iuni.data.hive.HiveConnector;
import com.iuni.data.hive.HiveOperator;
import com.iuni.data.impala.ImpalaConnector;
import com.iuni.data.impala.ImpalaOperator;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * analyze page kpi
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PageAnalyze extends AbstractAnalyze {

    private static final Logger logger = LoggerFactory.getLogger(PageAnalyze.class);

//    protected String hiveDriver;
//    protected String hiveUrl;
//    protected String hiveUser;
//    protected String hivePassword;
//    protected String impalaDriver;
//    protected String impalaUrl;

    protected HiveOperator hiveOperator;
    protected ImpalaOperator impalaOperator;
    protected String logTableName;

    public PageAnalyze() {
        super();
    }

    /**
     * @param context the context of config info from config file
     */
    @Override
    public void configure(Context context) {
        super.configure(context);

        // setBasicInfoForCreate partition flag
        createPartition = context.getBoolean(Constants.createPartition, Constants.CREATE_PARTITION_DEFAULT);

        // hive config
        String hiveDriver = context.getString(Constants.hiveDriver);
        Preconditions.checkState(hiveDriver != null, "The parameter " + Constants.hiveDriver + " must be specified");
        String hiveUrl = context.getString(Constants.hiveUrl);
        Preconditions.checkState(hiveUrl != null, "The parameter " + Constants.hiveUrl + " must be specified");
        String hiveUser = context.getString(Constants.hiveUser, Constants.HIVE_USER_DEFAULT);
        Preconditions.checkState(hiveUser != null, "The parameter " + Constants.hiveUser + " must be specified");
        String hivePassword = context.getString(Constants.hivePassword, "");
        Preconditions.checkState(hivePassword != null, "The parameter " + Constants.hivePassword + " must be specified");

        HiveConnector hiveConnector = new HiveConnector(hiveUrl, hiveDriver, hiveUser, hivePassword);
        hiveOperator = new HiveOperator(hiveConnector);

        // impala config
        String impalaDriver = context.getString(Constants.impalaDriver);
        Preconditions.checkState(impalaDriver != null, "The parameter " + Constants.impalaDriver + " must be specified");
        String impalaUrl = context.getString(Constants.impalaUrl);
        Preconditions.checkState(impalaUrl != null, "The parameter " + Constants.impalaUrl + " must be specified");

        ImpalaConnector impalaConnector = new ImpalaConnector(impalaUrl, impalaDriver);
        impalaOperator = new ImpalaOperator(impalaConnector);

        logTableName = context.getString(Constants.hiveTableName, Constants.HIVE_TABLE_NAME);
    }

    @Override
    public void start() {
        logger.info("pageAnalyze starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("pageAnalyze stopping");
        super.stop();
    }

    /**
     * init hive operator, connection and impala operator connection
     *
     * @param startTime       the start time of analyze
     * @param endTime         the end time of analyze
     * @param createPartition need setBasicInfoForCreate partition or not
     */
    protected void init(Date startTime, Date endTime, boolean createPartition) {
        // if createPartition is true, then parse and add partitions
//        if (createPartition) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        if (createPartition || (calendar.get(Calendar.HOUR) == 0 && calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0))
            hiveOperator.parseAndAddPartitions(logTableName, startTime, endTime);
        // invalidate metadata
        impalaOperator.invalidateMetadata();
    }

    /**
     * use impala or hive to analyze page pv and uv
     * timeStr is like 20150101000000
     *
     * @param startTime       the start time of analyze
     * @param endTime         the end time of analyze
     * @param tType           the time type of analyze
     * @param createPartition need setBasicInfoForCreate partition or not
     */
    @Override
    public void analyze(String startTime, String endTime, TType tType, boolean createPartition) {
        Date startDate = DateUtils.simpleDateStrToDate(startTime);
        Date endDate = DateUtils.simpleDateStrToDate(endTime);
        analyze(startDate, endDate, tType, createPartition);
    }

    /**
     * when createPartition is true, then just setBasicInfoForCreate partition, else analyze data
     *
     * @param startTime       the start time of analyze
     * @param endTime         the end time of analyze
     * @param tType           the time type of analyze, day/hour/minute eg.
     * @param createPartition need setBasicInfoForCreate partition or not
     */
    @Override
    public void analyze(Date startTime, Date endTime, TType tType, boolean createPartition) {
        init(startTime, endTime, createPartition);

        Map<Date, Date> timeRangeMap = DateUtils.parseTimeRange(startTime, endTime, tType);
        for (Map.Entry<Date, Date> entry : timeRangeMap.entrySet()) {
            Result result = new Result();
            result.settType(tType);
            result.setTime(entry.getKey());
            // analyze page pv and uv
            result.addPageWebKpi(analyzePage(tType, entry.getKey(), entry.getKey().getTime(), entry.getValue().getTime()));
            DataCube.addResult(result);
        }
    }

    /**
     * use impala or hive to analyze page pv and uv
     *
     * @param tType          the time type of analyze
     * @param time           the time of partition
     * @param startTimeStamp the start time of analyze
     * @param endTimeStamp   the end time of analyze
     * @return pageWebKpiMap
     */
    private Map<String, PageWebKpi> analyzePage(TType tType, Date time, long startTimeStamp, long endTimeStamp) {
        Map<String, PageWebKpi> pageWebKpiMap = new HashMap<>();
        analyzePagePv(tType, time, startTimeStamp, endTimeStamp, pageWebKpiMap);
        analyzePageUv(tType, time, startTimeStamp, endTimeStamp, pageWebKpiMap);
        return pageWebKpiMap;
    }

    /**
     * use impala or hive to analyze pv
     *
     * @param tType          the time type of analyze
     * @param time           the time of partition
     * @param startTimeStamp the start time of analyze
     * @param endTimeStamp   the end time of analyze
     * @param pageWebKpiMap  result of analyze
     */
    private void analyzePagePv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, PageWebKpi> pageWebKpiMap) {
        logger.info("analyze page pv of {}:{} started", tType, time);
        // pv
        String pvSqlStr = "select adId, url, count(*) from " + logTableName + " where 1 = 1 " + transTimeCondition(time, startTimeStamp, endTimeStamp) + " group by adId, url";
        List<List<String>> pvResultList = impalaOperator.query(pvSqlStr);
        for (List<String> pvResult : pvResultList) {
            String adId = pvResult.get(0);
            String url = pvResult.get(1);
            url = UrlUtils.completeUrl(url);
            Channel channel = DataCube.findChannelByCode(adId);
            String key = url + channel.getId();
            PageWebKpi webKpi = pageWebKpiMap.get(key);
            if (webKpi == null) {
                webKpi = new PageWebKpi();
                webKpi.setTime(time);
                webKpi.setTtype(tType.getPattern());
                webKpi.setPage(url);
                webKpi.setChannel(channel);
                pageWebKpiMap.put(key, webKpi);
            }
            int pv = Integer.parseInt(pvResult.get(2));
            webKpi.setPv(webKpi.getPv() + pv);
        }
        logger.info("analyze page pv of {}:{} finished.", tType, time);
    }

    /**
     * use impala or hive to analyze uv
     *
     * @param tType          the time type of analyze
     * @param time           the time of partition
     * @param startTimeStamp the start time of analyze
     * @param endTimeStamp   the end time of analyze
     * @param pageWebKpiMap  result of analyze
     */
    private void analyzePageUv(TType tType, Date time, long startTimeStamp, long endTimeStamp, Map<String, PageWebKpi> pageWebKpiMap) {
        logger.info("analyze page uv of {}:{} started", tType, time);
        String uvSqlStr = "select adId, url, count(distinct cookie_vk) from " + logTableName + " where 1 = 1 " + transTimeCondition(time, startTimeStamp, endTimeStamp) + " group by adId, url";
        List<List<String>> uvResultList = impalaOperator.query(uvSqlStr);
        for (List<String> uvResult : uvResultList) {
            String adId = uvResult.get(0);
            String url = uvResult.get(1);
            url = UrlUtils.completeUrl(url);
            Channel channel = DataCube.findChannelByCode(adId);
            String key = url + channel.getId();
            PageWebKpi webKpi = pageWebKpiMap.get(key);
            if (webKpi == null) {
                webKpi = new PageWebKpi();
                webKpi.setTime(time);
                webKpi.setTtype(tType.getPattern());
                webKpi.setPage(url);
                webKpi.setChannel(channel);
                pageWebKpiMap.put(key, webKpi);
            }
            int uv = Integer.parseInt(uvResult.get(2));
            webKpi.setUv(webKpi.getUv() + uv);
        }
        logger.info("analyze page uv of {}:{} finished", tType, time);
    }

    /**
     * Generate time condition
     *
     * @param time           the time of partition
     * @param startTimeStamp the start timestamp of analyze
     * @param endTimeStamp   the end timestamp of analyze
     * @return String
     */
    protected String transTimeCondition(Date time, long startTimeStamp, long endTimeStamp) {
        StringBuilder sbd = new StringBuilder();
        String timeStr = DateUtils.dateToSimpleDateStrOfDay(time);
        sbd.append(" and time = ").append(timeStr);
        sbd.append(" and `timestamp` >= ").append(startTimeStamp);
        sbd.append(" and `timestamp` < ").append(endTimeStamp);
        return sbd.toString();
    }

}
