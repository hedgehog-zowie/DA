package com.iuni.data.impala;

import com.iuni.data.common.Constants;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.exceptions.IuniDADateFormatException;
import com.iuni.data.exceptions.IuniDAImpalaException;
import com.iuni.data.hive.HiveConnector;
import com.iuni.data.hive.HiveOperator;
import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.Holiday;
import com.iuni.data.persist.domain.webkpi.WebKpi;
import com.iuni.data.persist.repository.config.FlowSourceRepository;
import com.iuni.data.persist.repository.config.HolidayRepository;
import com.iuni.data.persist.repository.webkpi.WebKpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service("webkpiAnalyze")
public class WebkpiAnalyze {
    private static Logger logger = LoggerFactory.getLogger(WebkpiAnalyze.class);
    private static final String IMPALA_CONF_FILE = "impala.properties";
    private static String hiveUrl;
    private static String hiveUser;
    private static String hivePassword;

    private ImpalaConnector connector;
    private ImpalaOperator operator;
    private HiveConnector hiveConnector;
    private HiveOperator hiveOperator;

    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private FlowSourceRepository flowSourceRepository;
    @Autowired
    private WebKpiRepository webKpiRepository;

    /**
     * 来源列表
     */
    private List<FlowSource> flowSourceList;
    /**
     * 默认为其他来源
     */
    private FlowSource defaultSource;
    /**
     * 工作日/非工作日列表
     */
    private List<Holiday> holidayList;

    public ImpalaConnector getConnector() {
        return connector;
    }

    public void setConnector(ImpalaConnector connector) {
        this.connector = connector;
    }

    public ImpalaOperator getOperator() {
        return operator;
    }

    public void setOperator(ImpalaOperator operator) {
        this.operator = operator;
    }

    public WebkpiAnalyze() throws Exception {
        init();
    }

    private void init() throws Exception {
        ImpalaConfig impalaConfig = new ImpalaConfig(IMPALA_CONF_FILE);
        connector = new ImpalaConnector(impalaConfig.getUrl());
        operator = new ImpalaOperator(connector);

        hiveUrl = impalaConfig.getHiveUrl();
        hiveUser = impalaConfig.getHiveUser();
        hivePassword = impalaConfig.getHivePassword();
        hiveConnector = new HiveConnector(hiveUrl, hiveUser, hivePassword);
        hiveOperator = new HiveOperator(hiveConnector);
    }

    /**
     * 首先查找出这段时间所有会话的真实来源，以其第一次访问的来源来准
     *
     * @param timeStr
     * @param tType
     * @return
     */
    private String selectRealReferer(String timeStr, TType tType) {
        return new StringBuilder("select t1.realReferer , t2.* from ")
                .append("(select cookie_sessionid, max(referer) as realReferer from ")
                .append(Constants.hiveCurrentTableName).append(" where connection_requests = 1 ")
                .append(transWhereCondition(timeStr, tType))
                .append(" group by cookie_sessionid) t1, ")
                .append(Constants.hiveCurrentTableName).append(" t2 where t1.cookie_sessionid = t2.cookie_sessionid")
                .append(transWhereCondition(timeStr, tType))
                .toString();
    }

    /**
     * 生成where条件
     *
     * @param timeStr
     * @param tType
     * @return
     */
    private String transWhereCondition(String timeStr, TType tType) {
        StringBuilder sbd = new StringBuilder();
        if (!(TType.YEAR.getValue() > tType.getValue())) {
            String year = timeStr.substring(0, 4);
            sbd.append(" and year = ").append(year);
        }
        if (!(TType.MONTH.getValue() > tType.getValue())) {
            String month = timeStr.substring(4, 6);
            sbd.append(" and month = ").append(month);
        }
        if (!(TType.DAY.getValue() > tType.getValue())) {
            String day = timeStr.substring(6, 8);
            sbd.append(" and day = ").append(day);
        }
        if (!(TType.HOUR.getValue() > tType.getValue())) {
            String hour = timeStr.substring(8, 10);
            sbd.append(" and hour = ").append(hour);
        }
        if (!(TType.MINUTE.getValue() > tType.getValue())) {
            String minute = timeStr.substring(10, 12);
            sbd.append(" and minute = ").append(minute);
        }
        return sbd.toString();
    }

    /**
     * 匹配配置的流量来源
     *
     * @param url
     * @return
     */
    private FlowSource findSourceByUrl(String url) {
        for (FlowSource flowSource : flowSourceList) {
            if (url.contains(flowSource.getUrl()))
                return flowSource;
        }
        return defaultSource;
    }

    /**
     * 匹配节假日
     *
     * @return
     */
    private boolean isHoliday(Date date) {
        for (Holiday holiday : holidayList) {
            // 开始时间减1ms，结束时间需加1天，即24 * 60 * 60 * 1000ms
            if (holiday.getStartDate().getTime() - 1 < date.getTime() && date.getTime() < holiday.getEndDate().getTime() + 24 * 60 * 60 * 1000)
                return holiday.getHolidayType().getId() == 3 ? false : true;
        }
        // 未找到配置的记录，则判断日期是否为周末，Calendar 以1-7表示，星期日为第一天，类推
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek > 1 && dayOfWeek < 7 ? false : true;
    }

    /**
     * 按地域统计浏览量，总响应时长，总响应大小
     *
     * @param timeStr
     * @param tType
     * @param webKpiMap
     * @return
     */
    private void getPv(String timeStr, TType tType, Map<String, WebKpi> webKpiMap) {
        // 若时间不符合规则
        Date time;
        try {
            time = DateUtils.simpleDateStrToDate(timeStr);
        } catch (IuniDADateFormatException e) {
            logger.error("timeStr format error, check time: {}", timeStr);
            return;
        }

        String sqlStr = new StringBuilder("select country, area, region, city, couty, isp, realReferer, count(*), sum(request_time), sum(body_bytes_sent) from (")
                .append(selectRealReferer(timeStr, tType))
                .append(")t group by country, area, region, city, couty, isp, realReferer")
                .toString();
        ResultSet resultSet = null;
        try {
            // delete by zweig in 20150212
//            resultSet = operator.query(sqlStr);
            while (resultSet.next()) {
                String country = resultSet.getString(1);
                String area = resultSet.getString(2);
                String province = resultSet.getString(3);
                String city = resultSet.getString(4);
                String county = resultSet.getString(5);
                String isp = resultSet.getString(6);
                FlowSource flowSource = findSourceByUrl(resultSet.getString(7));
                String key = country + area + province + city + county + isp + flowSource.getId();
                WebKpi webKpi = webKpiMap.get(key);
                if (webKpi == null) {
//                    webkpi = new Webkpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                    webKpiMap.put(key, webKpi);
                }
                int pv = resultSet.getInt(8);
                webKpi.setPv(webKpi.getPv() + pv);
                // 总时长转换为秒
                long totalTime = (long) (resultSet.getDouble(9) * 1000);
                webKpi.setTotalTime(webKpi.getTotalTime() + totalTime);
                long totalSize = resultSet.getLong(10);
                webKpi.setTotalSize(webKpi.getTotalSize() + totalSize);
            }
        } catch (IuniDAImpalaException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } catch (SQLException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    ;
                }
                try {
                    resultSet.getStatement().close();
                } catch (SQLException e) {
                    ;
                }
            }
        }
    }

    /**
     * 按地域统计独立用户，因为地域不一样，可认为其为不同的用户
     *
     * @param timeStr
     * @param tType
     * @param webKpiMap
     * @return
     */
    private void getUv(String timeStr, TType tType, Map<String, WebKpi> webKpiMap) {
        // 若时间不符合规则
        Date time;
        try {
            time = DateUtils.simpleDateStrToDate(timeStr);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
            return;
        }
        String tmpSql = selectRealReferer(timeStr, tType);
        // 查询独立用户
        String sqlStr = new StringBuilder("select country, area, region, city, couty, isp, realReferer, count(distinct cookie_vk) from (")
                .append(tmpSql)
                .append(")t group by country, area, region, city, couty, isp, realReferer")
                .append(" order by country, area, region, city, couty, isp, realReferer")
                .toString();
        ResultSet resultSet = null;
        try {
            // delete by zweig in 20150212
//            resultSet = operator.query(sqlStr);
            while (resultSet.next()) {
                String country = resultSet.getString(1);
                String area = resultSet.getString(2);
                String province = resultSet.getString(3);
                String city = resultSet.getString(4);
                String county = resultSet.getString(5);
                String isp = resultSet.getString(6);
                FlowSource flowSource = findSourceByUrl(resultSet.getString(7));
                String key = country + area + province + city + county + isp + flowSource.getId();
                WebKpi webKpi = webKpiMap.get(key);
                if (webKpi == null) {
//                    webkpi = new Webkpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                    webKpiMap.put(key, webKpi);
                }
                int uv = resultSet.getInt(8);
                webKpi.setUv(webKpi.getUv() + uv);
            }
        } catch (IuniDAImpalaException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } catch (SQLException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    ;
                }
                try {
                    resultSet.getStatement().close();
                } catch (SQLException e) {
                    ;
                }
            }
        }
    }

    /**
     * 按地域统计独立IP，因为地域不一样，可认为其为不同的用户
     *
     * @param timeStr
     * @param tType
     * @param webKpiMap
     */
    private void getIp(String timeStr, TType tType, Map<String, WebKpi> webKpiMap) {
        // 若时间不符合规则
        Date time;
        try {
            time = DateUtils.simpleDateStrToDate(timeStr);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
            return;
        }
        // 查询独立IP
        String sqlStr = new StringBuilder("select country, area, region, city, couty, isp, realReferer, count(distinct remote_addr) from (")
                .append(selectRealReferer(timeStr, tType))
                .append(")t group by country, area, region, city, couty, isp, realReferer")
                .append(" order by country, area, region, city, couty, isp, realReferer")
                .toString();
        ResultSet resultSet = null;
        try {
            // delete by zweig in 20150212
//            resultSet = operator.query(sqlStr);
            while (resultSet.next()) {
                String country = resultSet.getString(1);
                String area = resultSet.getString(2);
                String province = resultSet.getString(3);
                String city = resultSet.getString(4);
                String county = resultSet.getString(5);
                String isp = resultSet.getString(6);
                FlowSource flowSource = findSourceByUrl(resultSet.getString(7));
                String key = country + area + province + city + county + isp + flowSource.getId();
                WebKpi webKpi = webKpiMap.get(key);
                if (webKpi == null) {
//                    webkpi = new Webkpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                    webKpiMap.put(key, webKpi);
                }
                int ip = resultSet.getInt(8);
                webKpi.setIp(webKpi.getIp() + ip);
            }
        } catch (IuniDAImpalaException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } catch (SQLException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    ;
                }
                try {
                    resultSet.getStatement().close();
                } catch (SQLException e) {
                    ;
                }
            }
        }
    }

    /**
     * 按地域统计新独立用户，因为地域不一样，可认为其为不同的用户
     *
     * @param timeStr
     * @param tType
     * @param webKpiMap
     * @return
     */
    private void getNewUv(String timeStr, TType tType, Map<String, WebKpi> webKpiMap) {
        // 若时间不符合规则
        Date time;
        try {
            time = DateUtils.simpleDateStrToDate(timeStr);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
            return;
        }
        // 临时表，取出每次访问的真实来源
        String tmpSql = selectRealReferer(timeStr, tType);
        // 查找旧UV个数
        String sqlStr = new StringBuilder("select country, area, region, city, couty, isp, realReferer, count(distinct cookie_vk) from ")
                .append("(select country, area, region, city, couty, isp, realReferer, TT2.cookie_vk from (")
                .append(tmpSql)
                .append(") TT1, ")
                .append("(select distinct(cookie_vk) from ")
                .append(Constants.hiveCurrentTableName).append(" where time < ")
                .append(timeStr.substring(0, 12))
                .append(") TT2 ")
                .append("where TT1.cookie_vk = TT2.cookie_vk) NEWUV")
                .append(" group by country, area, region, city, couty, isp, realReferer")
                .append(" order by country, area, region, city, couty, isp, realReferer")
                .toString();
        ResultSet resultSet = null;
        try {
            // delete by zweig in 20150212
//            resultSet = operator.query(sqlStr);
            while (resultSet.next()) {
                String country = resultSet.getString(1);
                String area = resultSet.getString(2);
                String province = resultSet.getString(3);
                String city = resultSet.getString(4);
                String county = resultSet.getString(5);
                String isp = resultSet.getString(6);
                FlowSource flowSource = findSourceByUrl(resultSet.getString(7));
                String key = country + area + province + city + county + isp + flowSource.getId();
                WebKpi webKpi = webKpiMap.get(key);
                if (webKpi == null) {
//                    webkpi = new Webkpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                    webKpiMap.put(key, webKpi);
                }
                int newUv = resultSet.getInt(8);
                webKpi.setNewUv(webKpi.getNewUv() + newUv);
            }
        } catch (IuniDAImpalaException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } catch (SQLException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    ;
                }
                try {
                    resultSet.getStatement().close();
                } catch (SQLException e) {
                    ;
                }
            }
        }
    }

    /**
     * 获取访问次数，跳出率次数，停留时间
     *
     * @param timeStr
     * @param tType
     * @param webKpiMap
     * @return
     */
    private void getVv(String timeStr, TType tType, Map<String, WebKpi> webKpiMap) {
        // 若时间不符合规则
        Date time;
        try {
            time = DateUtils.simpleDateStrToDate(timeStr);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
            return;
        }
        String sqlStr = new StringBuilder("select country, area, region, city, couty, isp, realReferer," +
                " count(*), max(`timestamp`) - min(`timestamp`) from (")
                .append(selectRealReferer(timeStr, tType))
                .append(") t group by country, area, region, city, couty, isp, cookie_sessionid, realReferer")
                .toString();
        ResultSet resultSet = null;
        try {
            // delete by zweig in 20150212
//            resultSet = operator.query(sqlStr);
            while (resultSet.next()) {
                String country = resultSet.getString(1);
                String area = resultSet.getString(2);
                String province = resultSet.getString(3);
                String city = resultSet.getString(4);
                String county = resultSet.getString(5);
                String isp = resultSet.getString(6);
                FlowSource flowSource = findSourceByUrl(resultSet.getString(7));
                String key = country + area + province + city + county + isp + flowSource.getId();
                WebKpi webKpi = webKpiMap.get(key);
                if (webKpi == null) {
//                    webkpi = new Webkpi(area, city, country, county, new Date(), isp, province, time, tType.getPattern(), flowSource, channel);
                    webKpiMap.put(key, webKpi);
                }
                // 每个SID的访问次数
                int vTimes = resultSet.getInt(8);
                // 若该session只有一次访问，跳出数加1
                if (vTimes == 1)
                    webKpi.setTotalJump(webKpi.getTotalJump() + 1);
                // VV + 1
                webKpi.setVv(webKpi.getVv() + 1);
                // 页面停留时间
                int sTime = resultSet.getInt(9);
                webKpi.setStayTime(webKpi.getStayTime() + sTime);
            }

        } catch (IuniDAImpalaException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } catch (SQLException e) {
            logger.error("query data from impala error, error msg is: {}", e.getLocalizedMessage());
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    ;
                }
                try {
                    resultSet.getStatement().close();
                } catch (SQLException e) {
                    ;
                }
            }
        }
    }

    /**
     * 添加分区
     * @param startTime
     * @param endTime
     */
    public void addPartitions(String startTime, String endTime){
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.simpleDateStrToDate(startTime);
            endDate = DateUtils.simpleDateStrToDate(endTime);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
        }

        long parCur = startDate.getTime();
        while(parCur < endDate.getTime()){
            // 分区日期
            Date parDate = new Date(parCur);
            // 创建分区
            try {
                hiveOperator.parseAndAddPartition(Constants.hiveCurrentTableName, parDate);
            } catch (IuniDADateFormatException e) {
                logger.error(e.getLocalizedMessage());
            }
            parCur += 60 * 1000;
        }
    }

    /**
     * 分析流量数据
     *
     * @param startTime
     * @param endTime
     * @param tType
     * @return
     */
    public List<WebKpi> analyze(String startTime, String endTime, TType tType) {
        flowSourceList = flowSourceRepository.findByStatusAndCancelFlag(1, 0);
        holidayList = holidayRepository.findByStatusAndCancelFlag(1, 0);
        defaultSource = flowSourceRepository.findOne((long) 99999);

        List<WebKpi> webKpiList = new ArrayList<WebKpi>();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.simpleDateStrToDate(startTime);
            endDate = DateUtils.simpleDateStrToDate(endTime);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
        }

        long cur = startDate.getTime();
        while (cur < endDate.getTime()) {
            // 统计日期
            Date date = new Date(cur);

            // 判断是否为工作日
            boolean workDay = isHoliday(date) ? false : true;
            try {
                operator.invalidateMetadata();
            } catch (IuniDAImpalaException e) {
                logger.error(e.getLocalizedMessage());
            }
            // 日期转换
            String parDateStr = null;
            try {
                parDateStr = DateUtils.dateToSimpleDateStr(date);
            } catch (IuniDADateFormatException e) {
                logger.error(e.getLocalizedMessage());
                continue;
            }
            Map<String, WebKpi> webkpiMap = new HashMap<String, WebKpi>();
            // 分析PV等
            getPv(parDateStr, tType, webkpiMap);
            // 分析UV
            getUv(parDateStr, tType, webkpiMap);
            // 分析IP
            getIp(parDateStr, tType, webkpiMap);
            // 分析新UV
            getNewUv(parDateStr, tType, webkpiMap);
            // 分析VV等
            getVv(parDateStr, tType, webkpiMap);
            for (Map.Entry<String, WebKpi> entry : webkpiMap.entrySet()) {
                WebKpi webKpi = entry.getValue();
                // 1表示工作日，默认值0表示非工作日
                if (workDay)
                    webKpi.setWorkday(1);
                webKpiList.add(webKpi);
                webKpiRepository.save(webKpi);
            }
            switch (tType) {
                case YEAR:
                    cur += 365 * 24 * 60 * 60 * 1000;
                    break;
                case MONTH:
                    cur += 30 * 24 * 60 * 60 * 1000;
                    break;
                case DAY:
                    cur += 24 * 60 * 60 * 1000;
                    break;
                case HOUR:
                    cur += 60 * 60 * 1000;
                    break;
                case MINUTE:
                    cur += 60 * 1000;
                    break;
                default:
                    cur += 60 * 60 * 1000;
                    break;
            }
            logger.info("{} finished.", date);
        }
//        webKpiRepository.save(webkpiList);
        return webKpiList;
    }

    public FlowSourceRepository getFlowSourceRepository() {
        return flowSourceRepository;
    }

    public void setFlowSourceRepository(FlowSourceRepository flowSourceRepository) {
        this.flowSourceRepository = flowSourceRepository;
    }

}
