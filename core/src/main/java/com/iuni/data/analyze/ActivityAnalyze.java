package com.iuni.data.analyze;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.analyze.cube.DataCube;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.Constants;
import com.iuni.data.common.DataType;
import com.iuni.data.common.TType;
import com.iuni.data.exceptions.IuniDADateFormatException;
import com.iuni.data.hbase.HBaseOperator;
import com.iuni.data.hbase.field.ClickField;
import com.iuni.data.hive.HiveConnector;
import com.iuni.data.hive.HiveOperator;
import com.iuni.data.impala.ImpalaConnector;
import com.iuni.data.impala.ImpalaOperator;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.config.RTagType;
import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.utils.UrlUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * analyze active page's pv, uv and the count of buttons on those page have been clicked.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("activityAnalyze")
public class ActivityAnalyze extends AbstractAnalyze {

    private static final Logger logger = LoggerFactory.getLogger(ActivityAnalyze.class);

    private HiveOperator hiveOperator;
    private ImpalaOperator impalaOperator;
    private HBaseOperator hBaseOperator;
    private String logTableName;

    private String pageReportTableName;
    private String pageReportTableName_cf;
    @Deprecated
    private String pageReportTableName_columnName;

    // init flag
    private boolean init;
    // channel map
    private Map<String, Channel> channelMap;
    // page tag list
    private Map<String, RTag> rTagMap;
    // activity rTag type
    private RTagType rTagType;

    public ActivityAnalyze() {
        super();
    }

    /**
     * @param context the context of config info from config file
     */
    @Override
    public void configure(Context context) {
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

        // hive log table config
        logTableName = context.getString(Constants.hiveCurrentTableName, Constants.HIVE_TABLE_NAME_CURRENT);

        // hbase config
        String hbaseQuorum = context.getString(Constants.hbaseQuorum);
        Preconditions.checkState(hbaseQuorum != null, "The parameter " + Constants.hbaseQuorum + " must be specified");

        hBaseOperator = new HBaseOperator(hbaseQuorum);

        // hbase log table config
        pageReportTableName = context.getString(Constants.pageReportDataTable, Constants.pageReportDataTableDefault);
        pageReportTableName_cf = context.getString(Constants.pageReportDataTableCf, Constants.pageReportDataTableCfDefault);
        pageReportTableName_columnName = context.getString(Constants.pageReportDataTableColumn, Constants.pageReportDataTableColumnDefault);
    }

    @Override
    public void start() {
        logger.info("ActivityAnalyze starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("ActivityAnalyze stopping");
        super.stop();
    }

    /**
     * when createPartition is true, then just setBasicInfoForCreate partition, else analyze data
     *
     * @param startTime       the start time of analyze
     * @param endTime         the end time of analyze
     * @param tType           the time type of analyze, day/hour/minute eg.
     * @param createPartition need setBasicInfoForCreate partition or not
     */
    public void analyze(Date startTime, Date endTime, TType tType, boolean createPartition) {
        Result result = new Result();
        result.settType(tType);
        // if createPartition is true, then parse and add partitions
        if (createPartition) {
            hiveOperator.parseAndAddPartitions(logTableName, startTime, endTime);
        }
        // invalidate metadata
        impalaOperator.invalidateMetadata();

        Map<Date, Date> timeRangeMap = DateUtils.parseTimeRange(startTime, endTime, tType);
        for (Map.Entry<Date, Date> entry : timeRangeMap.entrySet()) {
            // analyze page pv and uv
            result.addPageWebKpi(analyzePage(entry.getKey(), entry.getKey().getTime(), entry.getValue().getTime(), tType));
            // analyze click pv
            result.addClickPageWebKpi(analyzeClickPv(entry.getKey().getTime(), entry.getValue().getTime(), tType));
        }
    }

    /**
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
     * use impala or hive to analyze page pv and uv
     *
     * @param time           the time of partition
     * @param startTimeStamp the start time of analyze
     * @param endTimeStamp   the end time of analyze
     * @param tType          the time type of analyze
     */
    private Map<String, PageWebKpi> analyzePage(Date time, long startTimeStamp, long endTimeStamp, TType tType) {
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
                webKpi.setPv(0);
                webKpi.setUv(0);
                webKpi.setIp(0);
                webKpi.setVv(0);
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
     * @param startTimeStamp the start timestamp of analyze
     * @param endTimeStamp   the end timestamp of analyze
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
                webKpi.setPv(0);
                webKpi.setUv(0);
                webKpi.setIp(0);
                webKpi.setVv(0);
                pageWebKpiMap.put(key, webKpi);
            }
            int uv = Integer.parseInt(uvResult.get(2));
            webKpi.setUv(webKpi.getUv() + uv);
        }
        logger.info("analyze page uv of {}:{} finished", tType, time);
    }

    /**
     * analyze all click
     *
     * @param startTimeStamp the start timestamp of analyze
     * @param endTimeStamp   the end timestamp of analyze
     * @param tType          the time type of analyze
     */
    private Map<String, ClickWebKpi> analyzeClickPv(long startTimeStamp, long endTimeStamp, TType tType) {
        logger.info("analyze click pv of {}:{}-{} started", tType, startTimeStamp, endTimeStamp);
        Map<String, ClickWebKpi> clickWebKpiMap = new HashMap<>();
        Scan scan = new Scan();
        String dataType = DataType.CLICK.getName();
        String startRow = dataType + startTimeStamp;
        String stopRow = dataType + endTimeStamp;
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setStopRow(Bytes.toBytes(stopRow));
        scan.setBatch(Constants.default_batch_size);
        scan.setCaching(Constants.default_catch_size);
        scan.setCacheBlocks(Constants.default_catch_blocks);
        List<org.apache.hadoop.hbase.client.Result> resultList = hBaseOperator.query(pageReportTableName, scan);
        for (org.apache.hadoop.hbase.client.Result result : resultList) {
            String url = Bytes.toString(result.getValue(Bytes.toBytes(pageReportTableName_cf), Bytes.toBytes(ClickField.url.getRealFiled())));
            url = UrlUtils.completeUrl(url);
            if (StringUtils.isBlank(url))
                continue;

            String rTagCode = Bytes.toString(result.getValue(Bytes.toBytes(pageReportTableName_cf), Bytes.toBytes(ClickField.rTag.getRealFiled())));
            RTag rTag = DataCube.findRTagByCode(rTagCode, url);
            if (rTag == null) {
                logger.debug("not found rTag in db config. please check rTag config, rTag:{}, url: {}", rTagCode, url);
                continue;
            }

            String adIdStr = Bytes.toString(result.getValue(Bytes.toBytes(pageReportTableName_cf), Bytes.toBytes(ClickField.adId.getRealFiled())));
            Channel channel = DataCube.findChannelByCode(adIdStr);

            String key = url + rTagCode + adIdStr;
            ClickWebKpi clickWebKpi = clickWebKpiMap.get(key);
            if (clickWebKpi == null) {
                clickWebKpi = new ClickWebKpi();
                clickWebKpi.setChannel(channel);
                clickWebKpi.setRtag(rTag);
                clickWebKpi.setTime(checkTimeStamp(startTimeStamp));
                clickWebKpi.setTtype(tType.getPattern());
                clickWebKpi.setCount(0);
                clickWebKpiMap.put(key, clickWebKpi);
            }
            clickWebKpi.setCount(clickWebKpi.getCount() + 1);
        }
        logger.info("analyze click pv of {}:{}-{} finished", tType, startTimeStamp, endTimeStamp);
        return clickWebKpiMap;
    }

    /**
     * Generate page condition
     *
     * @return String
     */
    private String transPageCondition() {
        StringBuilder sbd = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, RTag> entry : rTagMap.entrySet()) {
            RTag rTag = entry.getValue();
            String info = rTag.getInfo().trim();
            info = UrlUtils.completeUrl(info);
            sbd.append("'").append(info).append("'");
            i++;
            if (i != rTagMap.size())
                sbd.append(",");
        }
        return sbd.toString();
    }

    /**
     * Generate time condition
     *
     * @param time           the time of partition
     * @param startTimeStamp the start timestamp of analyze
     * @param endTimeStamp   the end timestamp of analyze
     * @return String
     */
    private String transTimeCondition(Date time, long startTimeStamp, long endTimeStamp) {
        StringBuilder sbd = new StringBuilder();
        String timeStr = DateUtils.dateToSimpleDateStrOfDay(time);
        sbd.append(" and time = ").append(timeStr);
        sbd.append(" and `timestamp` >= ").append(startTimeStamp);
        sbd.append(" and `timestamp` < ").append(endTimeStamp);
        return sbd.toString();
    }

    /**
     * Check time string, return date
     *
     * @param timeStr the time need to check
     * @return Date
     */
    private Date checkTimeStr(String timeStr) {
        Date time = null;
        try {
            time = DateUtils.simpleDateStrToDate(timeStr);
        } catch (IuniDADateFormatException e) {
            logger.error("timeStr format error, check time: {}", timeStr);
        }
        return time;
    }

    /**
     * check timestamp, return date
     * timestamp is millisecond
     *
     * @param timeStamp the timestamp need to check
     * @return date
     */
    private Date checkTimeStamp(long timeStamp) {
        return DateUtils.millTimestampToDate(timeStamp);
    }

    /**
     * find channel
     *
     * @param code the code used to find channel
     * @return channel
     */
//    private Channel findChannelByCode(String code) {
//        if (StringUtils.isBlank(code))
//            code = Constants.DEFAULT_CHANNEL_CODE;
//        Channel channel = channelMap.get(code);
//        if (channel == null) {
//            // if channel is null, then setBasicInfoForCreate a new one
//            channel = ChannelUtils.newChannel(code);
//            channelMap.put(code, channel);
//        }
//        return channel;
//    }

    /**
     * setBasicInfoForCreate and save a new channel
     *
     * @param code the code used to find channel
     * @return channel
     */
//    private Channel newChannel(String code) {
//        Date date = new Date();
//        Channel newChannel = new Channel();
//        newChannel.setCancelFlag(0);
//        int codeLength = code.length() > 10 ? 10 : code.length();
//        newChannel.setCode(code.substring(0, codeLength));
//        newChannel.setCreateBy(this.getClass().getSimpleName());
//        newChannel.setCreateDate(date);
//        newChannel.setName(code.substring(0, codeLength));
//        newChannel.setStatus(1);
//        newChannel.setUpdateBy(this.getClass().getSimpleName());
//        newChannel.setUpdateDate(date);
////        return channelRepository.save(newChannel);
//        return newChannel;
//    }

    /**
     * find rTag in list
     *
     * @param code the code used to find rTag
     * @return rTag
     */
//    private RTag findRTagByCode(String code, String url) {
//        if (StringUtils.isBlank(code))
//            code = Constants.DEFAULT_RTAG_CODE;
//        return rTagMap.get(code + url);
//        // don't need to setBasicInfoForCreate a new one, just return null.
////        if (rTag == null) {
////            // if rTag is null, then setBasicInfoForCreate a new one
////            rTag = newRTag(code, url);
////            rTagMap.put(code + url, rTag);
////        }
//    }

    /**
     * setBasicInfoForCreate and save a new rTag
     *
     * @param code the code used to find rTag
     * @param url  the url used to find rTag
     * @return rTag
     */
//    private RTag newRTag(String code, String url) {
//        RTag rTag = new RTag();
//        // if not found this rTag
//        Date date = new Date();
//        rTag.setCancelFlag(0);
//        rTag.setCreateBy(this.getClass().getSimpleName());
//        rTag.setCreateDate(date);
//        int infoLength = url.length() > 1000 ? 1000 : url.length();
//        rTag.setInfo(url.substring(0, infoLength));
//        int codeLength = code.length() > 10 ? 10 : code.length();
//        rTag.setName(code.substring(0, codeLength));
//        rTag.setRtag(code.substring(0, codeLength));
//        rTag.setrTagType(rTagType);
//        rTag.setStatus(1);
//        rTag.setUpdateBy(this.getClass().getSimpleName());
//        rTag.setUpdateDate(date);
////        return pageTagRepository.save(rTag);
//        return rTag;
//    }

}
