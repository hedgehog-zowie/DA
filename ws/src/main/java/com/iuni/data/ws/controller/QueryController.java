package com.iuni.data.ws.controller;

import com.iuni.data.common.Constants;
import com.iuni.data.ws.common.Config;
import com.iuni.data.ws.common.QueryType;
import com.iuni.data.hbase.field.CgiField;
import com.iuni.data.hbase.field.ClickField;
import com.iuni.data.hbase.field.CommonField;
import com.iuni.data.hbase.field.PageField;
import com.iuni.data.ws.dto.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@RestController
@RequestMapping("/log/fetch")
public class QueryController {
    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    // 秒级时间戳长度
    private final static int TIMESTAMP_LENGTH = 10;
    // 毫秒级时间戳长度
    private final static int TIMESTAMP_MILLS_LENGTH = 13;

    @RequestMapping(produces = "application/json; charset=utf-8")
    @ResponseBody
    public String queryWebKpi(HttpServletRequest request) {
        String type = request.getParameter("type");
        String resultStr;
        if (StringUtils.isBlank(type)) {
            logger.error("receive query request, but type is blank.");
            resultStr = "please check parameter type.";
        } else {
            String startTime = request.getParameter("startTime").trim();
            String endTime = request.getParameter("endTime").trim();
            if (!StringUtils.isNumeric(startTime) || !StringUtils.isNumeric(endTime)) {
                logger.error("startTime or endTime is not correct., startTime:{}, endTime:{}", startTime, endTime);
                resultStr = "please check parameter startTime and endTime.";
            } else {
                if (startTime.length() == TIMESTAMP_LENGTH)
                    startTime += "000";
                if (endTime.length() == TIMESTAMP_LENGTH)
                    endTime += "000";
                if (startTime.length() != TIMESTAMP_MILLS_LENGTH || endTime.length() != TIMESTAMP_MILLS_LENGTH || endTime.compareTo(startTime) < 0) {
                    logger.error("startTime or endTime is not correct., startTime:{}, endTime:{}", startTime, endTime);
                    resultStr = "please check parameter startTime and endTime.";
                } else if(Long.parseLong(endTime) - Long.parseLong(startTime) > 24 * 60 * 60 * 1000){
                    logger.error("query time between beginTime and endTime can not be greater than 1 day, startTime:{}, endTime:{}", startTime, endTime);
                    resultStr = "query time between beginTime and endTime can not be greater than 1 day, please check parameter startTime and endTime.";
                } else {
                    logger.info("receive startTime: {}, endTime: {}, tType: {}", startTime, endTime, type);
                    if (QueryType.PV5MIN.getName().equals(type)) {
                        String url = request.getParameter("url");
                        String adId = request.getParameter("adId");
                        resultStr = setTestData(startTime, endTime);
                    }
                    else {
                        List<PageResult> resultList;
                        resultList = getData(type, Long.parseLong(startTime), Long.parseLong(endTime));
                        JSONArray jsonArray = JSONArray.fromObject(resultList);
                        resultStr = jsonArray.toString();
                    }
                }
            }
        }
        String dType = request.getParameter("dType");
        if ("jsonp".equals(dType)) {
            String callBack = request.getParameter("callback") == null ? "" : request.getParameter("callback");
            resultStr = new StringBuilder(callBack).append("(").append(resultStr).append(")").toString();
        }
        logger.info("return data: {}", resultStr);
        return resultStr;
    }

    @Deprecated
    private boolean checkQueryParam() {
        return true;
    }

    public List<PageResult> getData(String type, long startTimeStamp, long endTimeStamp) {
        List<PageResult> resultList = new ArrayList<>();
        try {
            HTable table = new HTable(Config.getConf(), Config.getTableName());
//            long startTimeStamp = startDate.getTime();
//            long endTimeStamp = endDate.getTime();
            String dataType = type;
            if (QueryType.PV.getName().equals(type) || QueryType.UV.getName().equals(type) || QueryType.PAGETIME.getName().equals(type))
                dataType = QueryType.PV.getName();
            String startRow = dataType + startTimeStamp;
            String stopRow = dataType + endTimeStamp;
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(stopRow));
            scan.setBatch(Constants.default_batch_size);
            scan.setCaching(Constants.default_catch_size);
            scan.setCacheBlocks(Constants.default_catch_blocks);
            ResultScanner resultScanner = table.getScanner(scan);
            if (QueryType.PV.getName().equals(type))
                resultList = parseResultToPv(resultScanner);
            else if (QueryType.UV.getName().equals(type))
                resultList = parseResultToUv(resultScanner);
            else if (QueryType.CGI.getName().equals(type))
                resultList = parseResultToCgi(resultScanner);
            else if (QueryType.CLICK.getName().equals(type))
                resultList = parseResultToClick(resultScanner);
            else if (QueryType.PAGETIME.getName().equals(type))
                resultList = parseResultToPageTime(resultScanner);
            // close scanner and table
            resultScanner.close();
            table.close();
        } catch (IOException e) {
            logger.error("scan HTable error, HTable is: {}, error msg is: {}",
                    Bytes.toString(Config.getTableName()), e.getLocalizedMessage());
            return resultList;
        }
        return resultList;
    }

    private List<PageResult> parseResultToPv(ResultScanner resultScanner) {
        List<PageResult> resultList = new ArrayList<>();
        Map<String, PV> pvMap = new HashMap<>();
        for (Result result : resultScanner) {
            String url = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(PageField.url.getRealFiled())));
            if (StringUtils.isBlank(url))
                continue;

            String adId = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(CommonField.ADID.getRealFiled())));
            adId = StringUtils.isBlank(adId) ? "" : adId;

            String key = url + adId;
            if (pvMap.containsKey(key))
                pvMap.get(key).setPv(pvMap.get(key).getPv() + 1);
            else
                pvMap.put(key, new PV(url, adId, 1));
        }
        for (Map.Entry<String, PV> entry : pvMap.entrySet())
            resultList.add(entry.getValue());
        Collections.sort(resultList);
        return resultList;
    }

    private List<PageResult> parseResultToUv(ResultScanner resultScanner) {
        List<PageResult> resultList = new ArrayList<>();
        Map<String, UV> uvMap = new HashMap<>();
        Set<UV> uvSet = new HashSet<>();
        for (Result result : resultScanner) {
            String url = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(PageField.url.getRealFiled())));
            if (StringUtils.isBlank(url))
                continue;

            String vk = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(CommonField.VK.getRealFiled())));
            // if vk is null, as a new vk.
            if (!StringUtils.isBlank(vk) || uvSet.contains(vk))
                continue;

            String adId = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(CommonField.ADID.getRealFiled())));
            adId = StringUtils.isBlank(adId) ? "" : adId;

            String key = url + adId;
            if (uvMap.containsKey(key))
                uvMap.get(key).setUv(uvMap.get(key).getUv() + 1);
            else
                uvMap.put(key, new UV(url, adId, 1));
        }
        for (Map.Entry<String, UV> entry : uvMap.entrySet())
            resultList.add(entry.getValue());
        return resultList;
    }

    private List<PageResult> parseResultToCgi(ResultScanner resultScanner) {
        List<PageResult> resultList = new ArrayList<>();
        Map<String, Cgi> cgiMap = new HashMap<>();
        for (Result result : resultScanner) {
            String url = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(CgiField.url.getRealFiled())));
            if (StringUtils.isBlank(url))
                continue;

            String state = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(CgiField.state.getRealFiled())));
            if (StringUtils.isBlank(state))
                continue;

            String timeStr = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(CgiField.time.getRealFiled())));
            Long time;
            if (StringUtils.isBlank(timeStr))
                timeStr = "0";
            time = Long.parseLong(timeStr);
            String key = url + state;
            if (cgiMap.containsKey(key)) {
                cgiMap.get(key).setCount(cgiMap.get(key).getCount() + 1);
                cgiMap.get(key).setAverageTime(cgiMap.get(key).getAverageTime() + time);
            } else {
                cgiMap.put(key, new Cgi(url, state, 1, time));
            }
        }
        for (Map.Entry<String, Cgi> entry : cgiMap.entrySet()) {
            Cgi cgi = entry.getValue();
            cgi.setAverageTime(cgi.getAverageTime() / cgi.getCount());
            resultList.add(entry.getValue());
        }
        return resultList;
    }

    private List<PageResult> parseResultToClick(ResultScanner resultScanner) {
        List<PageResult> resultList = new ArrayList<>();
        Map<String, Click> clickMap = new HashMap<>();
        for (Result result : resultScanner) {
            String url = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(ClickField.url.getRealFiled())));
            if (StringUtils.isBlank(url))
                continue;

            String rTag = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(ClickField.rTag.getRealFiled())));
            rTag = StringUtils.isBlank(rTag) ? "" : rTag;

            String adId = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(ClickField.adId.getRealFiled())));
            adId = StringUtils.isBlank(adId) ? "" : adId;

            String key = url + rTag + adId;
            if (clickMap.containsKey(key))
                clickMap.get(key).setCount(clickMap.get(key).getCount() + 1);
            else
                clickMap.put(key, new Click(rTag, url, 1));
        }
        for (Map.Entry<String, Click> entry : clickMap.entrySet())
            resultList.add(entry.getValue());
        return resultList;
    }

    /**
     * trans resultScanner to PageTime list
     *
     * @param resultScanner
     * @return
     */
    private List<PageResult> parseResultToPageTime(ResultScanner resultScanner) {
        List<PageResult> resultList = new ArrayList<>();
        Map<String, PageTime> pageTimeMap = new HashMap<>();
        for (Result result : resultScanner) {
            String url = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(PageField.url.getRealFiled())));
            if (StringUtils.isBlank(url))
                continue;

            String timeStr = Bytes.toString(result.getValue(Config.getCfName(), Bytes.toBytes(PageField.execTime.getRealFiled())));
            Long time;
            if (StringUtils.isBlank(timeStr))
                timeStr = "0";
            time = Long.parseLong(timeStr);

            String key = url;
            if (pageTimeMap.containsKey(key))
                pageTimeMap.get(key).setExecTime(pageTimeMap.get(key).getExecTime() + time);
            else
                pageTimeMap.put(key, new PageTime(url, time));
        }
        for (Map.Entry<String, PageTime> entry : pageTimeMap.entrySet())
            resultList.add(entry.getValue());
        return resultList;
    }

    public String setTestData(String startTimeStampStr, String endTimeStampStr){
        StringBuilder sbd = new StringBuilder("[");
        long startTimeStamp = Long.parseLong(startTimeStampStr);
        long endTimeStamp = Long.parseLong(endTimeStampStr);
        long i = startTimeStamp;
        Random random = new Random();
        while (i <= endTimeStamp) {
            sbd.append(Math.abs(random.nextInt() % 1000)).append(",");
            i += 5 * 60 * 1000;
        }
        String resultStr = sbd.toString();
        resultStr = resultStr.substring(0, resultStr.length() - 1) + "]";
        return resultStr;
    }

}

