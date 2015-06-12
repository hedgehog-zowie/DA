package com.iuni.data.analyze;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.analyze.cube.DataCube;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.*;
import com.iuni.data.common.field.ClickField;
import com.iuni.data.hbase.HBaseOperator;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * analyze click kpi
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ClickAnalyze extends AbstractAnalyze {

    private static final Logger logger = LoggerFactory.getLogger(ClickAnalyze.class);

    private String hbaseQuorum;

    private String pageReportTableName;
    private String pageReportTableName_cf;
    private String pageReportTableName_columnName;

    public ClickAnalyze() {
        super();
    }

    /**
     * @param context the context of config info from config file
     */
    @Override
    public void configure(Context context) {
        super.configure(context);
        // hbase config
        hbaseQuorum = context.getString(Constants.hbaseQuorum);
        Preconditions.checkState(hbaseQuorum != null, "The parameter " + Constants.hbaseQuorum + " must be specified");
        pageReportTableName = context.getString(Constants.pageReportDataTable, Constants.pageReportDataTableDefault);
        pageReportTableName_cf = context.getString(Constants.pageReportDataTableCf, Constants.pageReportDataTableCfDefault);
        pageReportTableName_columnName = context.getString(Constants.pageReportDataTableColumn, Constants.pageReportDataTableColumnDefault);
    }

    @Override
    public void start() {
        logger.info("clickAnalyze starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("ActivityAnalyze stopping");
        super.stop();
    }

    @Override
    public void analyze(String startTime, String endTime, TType tType, boolean createPartition) {
        Date startDate = DateUtils.simpleDateStrToDate(startTime);
        Date endDate = DateUtils.simpleDateStrToDate(endTime);
        analyze(startDate, endDate, tType, createPartition);
    }

    @Override
    public void analyze(Date startTime, Date endTime, TType tType, boolean createPartition) {
        Map<Date, Date> timeRangeMap = DateUtils.parseTimeRange(startTime, endTime, tType);
        for (Map.Entry<Date, Date> entry : timeRangeMap.entrySet()) {
            Result result = new Result();
            result.settType(tType);
            result.setTime(entry.getKey());
            // analyze click pv
            result.addClickPageWebKpi(analyzeClickPv(entry.getKey().getTime(), entry.getValue().getTime(), tType));
            DataCube.addResult(result);
        }
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
        HBaseOperator hBaseOperator = new HBaseOperator(hbaseQuorum);
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
                clickWebKpi.setTime(DateUtils.millTimestampToDate(startTimeStamp));
                clickWebKpi.setTtype(tType.getPattern());
                clickWebKpi.setCount(0);
                clickWebKpiMap.put(key, clickWebKpi);
            }
            clickWebKpi.setCount(clickWebKpi.getCount() + 1);
        }
        logger.info("analyze click pv of {}:{}-{} finished", tType, startTimeStamp, endTimeStamp);
        return clickWebKpiMap;
    }

}
