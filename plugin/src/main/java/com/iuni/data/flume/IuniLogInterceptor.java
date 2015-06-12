package com.iuni.data.flume;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.iuni.data.common.DateUtils;
import com.iuni.data.common.IuniStringUtils;
import com.iuni.data.common.UrlUtils;
import com.iuni.data.exceptions.IuniDADateFormatException;
import com.iuni.data.hbase.sindex.HbaseColumns;
import com.iuni.data.iplib.IpInfo;
import com.iuni.data.IpLib;
import com.iuni.data.iplib.MaterializedConfiguration;
import com.iuni.data.iplib.PollingPropertiesFileConfigurationProvider;
import com.iuni.data.lifecycle.LifecycleState;
import com.iuni.data.lifecycle.LifecycleSupervisor;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志过滤器（预处理）
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class IuniLogInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(IuniLogInterceptor.class);
    private final int statusIndex;
    private final int requestIndex;
    private final int requestTimeIndex;
    private final int ipIndex;
    private final int refererIndex;
    private final int userIndex;
    private final int sidIndex;
    private final String adIdFlag;
    private final List<StaticResSerializer> staticResList;
    private final String host;
    private final String spliter = "\\{\\]";
    private final int TIME_INDEX = 0;
    private final String defaultServerName = "www.iuni.com";
    private final int domainIndex;

    private final Date startDate;

    private IpLib ipLib;
    private final String ipLibName;
    private final String ipLibConf;
    private final LifecycleSupervisor supervisor;

    private IuniLogInterceptor(int statusIndex, int requestIndex, int domainIndex, int requestTimeIndex,
                               int ipIndex, int refererIndex, int userIndex, int sidIndex,
                               String adIdFlag, List<StaticResSerializer> staticResList,
                               String host, Date startDate, String ipLibName, String ipLibConf) {
        this.statusIndex = statusIndex;
        this.requestIndex = requestIndex;
        this.domainIndex = domainIndex;
        this.requestTimeIndex = requestTimeIndex;
        this.ipIndex = ipIndex;
        this.refererIndex = refererIndex;
        this.userIndex = userIndex;
        this.sidIndex = sidIndex;

        this.adIdFlag = adIdFlag;

        this.staticResList = staticResList;

        this.host = host;
        this.startDate = startDate;

        this.ipLibName = ipLibName;
        this.ipLibConf = ipLibConf;
        this.supervisor = new LifecycleSupervisor();
    }

    @Override
    public void initialize() {
        EventBus eventBus = new EventBus(this.ipLibName + "-event-bus");
        File configurationFile = new File(this.ipLibConf);
        PollingPropertiesFileConfigurationProvider configurationProvider =
                new PollingPropertiesFileConfigurationProvider(this.ipLibName, configurationFile, eventBus, 30);
        eventBus.register(this);

        // start
        supervisor.supervise(configurationProvider, new LifecycleSupervisor.SupervisorPolicy.AlwaysRestartPolicy(), LifecycleState.START);
        // wait configurationProvider start
        while (configurationProvider.getLifecycleState() != LifecycleState.START && !supervisor.isComponentInErrorState(configurationProvider)) {
            try {
                logger.info("Waiting for configurationProvider: " + ipLibConf + " to start. Sleeping for 1000 ms");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for configurationProvider to start.", e);
                Throwables.propagate(e);
            }
        }
    }

    @Subscribe
    public synchronized void handleConfigurationEvent(MaterializedConfiguration conf) {
        ipLib = conf.getIpLibs().get(ipLibName);
        if (ipLib == null)
            logger.error("not found ipLib: {}", ipLibName);
    }

    /**
     * 过滤 及 日志预处理
     *
     * @param event
     * @return
     */
    public Event intercept(Event event) {
        String bodyStr = new String(event.getBody(), Charsets.UTF_8);
        if (org.apache.commons.lang.StringUtils.isBlank(bodyStr)) {
            logger.error("log is empty.");
            return null;
        }
        String[] bodys = bodyStr.split(spliter);
        if (!(bodys.length > this.statusIndex && bodys.length > this.requestIndex && bodys.length > this.refererIndex)) {
            logger.error("log bodys max lager than statusIndex, requestIndex and refererIndex, " +
                            "log is: {}, statusIndex is: {}, requestIndex is: {}, refererIndex is: {}",
                    bodyStr, statusIndex, requestIndex, refererIndex);
            return null;
        }

        Map<String, String> headers = event.getHeaders();

        // 检查时间
        Date date;
        try {
            date = DateUtils.logDateStrToDate(bodys[TIME_INDEX]);
        } catch (IuniDADateFormatException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
        if (this.startDate != null && date.getTime() < this.startDate.getTime()) {
            logger.info("log date: {} is smaller than startDate: {}", date, startDate);
            return null;
        }
        // 添加时间戳到headers，若之前已经有过滤器添加过，则不再添加
        if (!headers.containsKey(HbaseColumns.COLUMN_TIME.getName())) {
            headers.put(HbaseColumns.COLUMN_TIME.getName(), Long.toString(date.getTime()));
        }

        // 添加host信息，表示从哪台服务器收集的日志
        if ((!headers.containsKey(Constants.HOST)) && (this.host != null)) {
            headers.put(Constants.HOST, this.host);
        }

        // 访问状态
        boolean statusFlag = checkStatusField(bodys[this.statusIndex]);

        // 分析request
        if (bodys.length > this.requestIndex) {
            String method = "";
            String requestUrl = "";
            String protocol = "";
            String adIdStr = "";
            String requestField = IuniStringUtils.trimQuotes(bodys[this.requestIndex]);

            String[] requestFields = requestField.split("\\s+");
            if (requestFields.length == 3) {
                String request = requestFields[1].trim();
                String[] arrayOfReqUrl = request.split("\\?");
                requestUrl = arrayOfReqUrl[0].trim();

                // 是否为静态资源
                boolean notStatic = checkRequestUrl(requestUrl);
                // 访问访问正常且非静态资源（或者为特殊的静态资源）,添加到usefull
                if (statusFlag && notStatic) {
                    headers.put(Constants.SUFFIX_USABLE, Constants.SUFFIX_USEFULL);
                }
                // 否则添加到useless
                else {
                    headers.put(Constants.SUFFIX_USABLE, Constants.SUFFIX_USELESS);
                }

                method = requestFields[0].trim();
                protocol = requestFields[2].trim();
                if (arrayOfReqUrl.length == 2) {
                    String paraStr = arrayOfReqUrl[1];
                    String[] params = paraStr.split("&");
                    for (int i = 0; i < params.length; i++) {
                        String param = params[i];
                        String[] arrayOfParam = param.split("=");
                        if ((this.adIdFlag.equals(arrayOfParam[0])) && (arrayOfParam.length == 2)) {
                            adIdStr = arrayOfParam[1];
                            break;
                        }
                    }
                }
            } else {
                headers.put(Constants.SUFFIX_USABLE, Constants.SUFFIX_USELESS);
            }
            if (!org.apache.commons.lang.StringUtils.isBlank(method)) {
                headers.put(HbaseColumns.COLUMN_METHOD.getName(), method);
            }
            if (!org.apache.commons.lang.StringUtils.isBlank(requestUrl)) {
                String serverName = defaultServerName;
                if (bodys.length > domainIndex) {
                    serverName = bodys[domainIndex].trim();
                }
                requestUrl = UrlUtils.completeUrl(requestUrl, serverName);
                headers.put(HbaseColumns.COLUMN_REQUEST_URL.getName(), requestUrl);
            }
            if (!org.apache.commons.lang.StringUtils.isBlank(protocol)) {
                headers.put(HbaseColumns.COLUMN_PROTOCOL_TYPE.getName(), protocol);
            }
            if (!org.apache.commons.lang.StringUtils.isBlank(adIdStr)) {
                headers.put(HbaseColumns.COLUMN_ADID.getName(), adIdStr);
            }
        } else {
            logger.info("log bodys max lager than urlIndex, log is: {}, urlIndex is: {}", bodyStr, this.requestIndex);
            return null;
        }
        // 分析referer
        if (bodys.length > this.refererIndex) {
            String referer;
            String refererField = bodys[this.refererIndex].trim();
            String[] refererFields = refererField.split("\\?");
            referer = IuniStringUtils.trimQuotes(refererFields[0]);
            if (!IuniStringUtils.isNullStr(referer)) {
                headers.put(HbaseColumns.COLUMN_HTTP_REFERER.getName(), referer);
            }
        }

        // 添加日志所在服务器机器名/IP，若之前已经有过滤器添加过，则不再添加
        if ((!headers.containsKey(HbaseColumns.COLUMN_HOST.getName())) && (this.host != null)) {
            headers.put(HbaseColumns.COLUMN_HOST.getName(), this.host);
        }

        if (bodys.length > this.ipIndex) {
            IpInfo ipinfo = null;
            try {
                ipinfo = ipLib.getIpInfo(bodys[this.ipIndex]);
            } catch (Exception e) {
                logger.error("get ipinfo failed, ip string is: {}, {}", bodys[this.ipIndex], e);
            }
            if (ipinfo != null) {
                if (!org.apache.commons.lang.StringUtils.isBlank(ipinfo.getCountry())) {
                    headers.put(HbaseColumns.COLUMN_COUNTRY.getName(), ipinfo.getCountry());
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(ipinfo.getArea())) {
                    headers.put(HbaseColumns.COLUMN_AREA.getName(), ipinfo.getArea());
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(ipinfo.getRegion())) {
                    headers.put(HbaseColumns.COLUMN_REGION.getName(), ipinfo.getRegion());
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(ipinfo.getCity())) {
                    headers.put(HbaseColumns.COLUMN_CITY.getName(), ipinfo.getCity());
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(ipinfo.getCounty())) {
                    headers.put(HbaseColumns.COLUMN_COUNTY.getName(), ipinfo.getCounty());
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(ipinfo.getIsp())) {
                    headers.put(HbaseColumns.COLUMN_ISP.getName(), ipinfo.getIsp());
                }
            }
        }

        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < bodys.length; i++) {
            String value = IuniStringUtils.trimQuotes(bodys[i]);
            if ((i == this.requestTimeIndex) && (!checkRequestTimeField(value)))
                value = "0.001";
            // 若检查到userIndex，且user非空，则将user添加到headers
            if (this.userIndex == i && !IuniStringUtils.isNullStr(value)) {
                headers.put(HbaseColumns.COLUMN_USER.getName(), value);
            }
            // 若检查到sidIndex, 且sid非空，则将sid添加到headers
            if (this.sidIndex == i && !IuniStringUtils.isNullStr(value)) {
                headers.put(HbaseColumns.COLUMN_SID.getName(), value);
            }
            sbd.append(value);
            if (i != bodys.length - 1)
                sbd.append("\001");
        }
        event.setBody(Bytes.toBytes(sbd.toString()));
        return event;
    }

    private boolean checkRequestTimeField(String requestTimeField) {
        return !"0.000".equals(requestTimeField.trim());
    }

    /**
     * 检查状态访问状态是否正常
     *
     * @param statusField
     * @return
     */
    private boolean checkStatusField(String statusField) {
        return statusField.trim().startsWith("2");
    }

    /**
     * 检查请求是否为需要的URL
     * 若为静态资源则返回false，若为特殊的静态资源返回true，其他非静态资源返回true
     *
     * @param requestUrl
     * @return
     */
    private boolean checkRequestUrl(String requestUrl) {
        if ((this.staticResList == null) || (this.staticResList.size() == 0)) {
            return true;
        }
        for (StaticResSerializer staticRes : this.staticResList) {
            String staticResName = staticRes.staticResName;
            // 若requestUrl以配置的静态资源后缀结束，检查是否为特殊的静态资源
            if ((!org.apache.commons.lang.StringUtils.isBlank(staticResName)) && (requestUrl.endsWith("." + staticResName))) {
                List<String> specialUrlList = staticRes.specialUrlList;
                // 若其特殊的静态资源为空 或 特殊的静态资源列表为空
                if ((specialUrlList == null) || (specialUrlList.size() == 0)) {
                    logger.info("request url is static url, request url: {}", requestUrl);
                    return false;
                }
                for (String specialUrl : specialUrlList) {
                    if ((!"".equals(specialUrl.trim())) && (requestUrl.contains(specialUrl))) {
                        logger.info("request url is special url, request url: {}, special is: {}", requestUrl, specialUrl);
                        return true;
                    }
                }
                logger.info("request url is static url, request url: {}", requestUrl);
                return false;
            }
        }
        return true;
    }

    public List<Event> intercept(List<Event> events) {
        List<Event> intercepted = Lists.newArrayListWithCapacity(events.size());
        for (Event event : events) {
            Event interceptedEvent = intercept(event);
            if (interceptedEvent != null) {
                intercepted.add(interceptedEvent);
            }
        }
        return intercepted;
    }

    public void close() {
    }

    public static class Builder implements Interceptor.Builder {
        private boolean useIP = true;
        private int statusIndex = 8;
        private int requestIndex = 7;
        private int domainIndex = 6;
        private int requestTimeIndex = 3;
        private int ipIndex = 4;
        private int refererIndex = 12;
        private int userIndex = 18;
        private int sidIndex = 19;
        private List<IuniLogInterceptor.StaticResSerializer> staticResList;
        private String host;
        private String adIdFlag;
        private Date startDate;
        private String ipLibName;
        private String ipLibConf;
//        private List<LifecycleAware> components = Lists.newArrayList();
//        private final LifecycleSupervisor supervisor = new LifecycleSupervisor();
//        private MaterializedConfiguration materializedConfiguration;

        public void configure(Context context) {
            this.useIP = context.getBoolean(Constants.USE_IP, Boolean.valueOf(Constants.USE_IP_DFLT)).booleanValue();
            this.statusIndex = context.getInteger(Constants.STATUS_INDEX, Integer.valueOf(Constants.STATUS_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.statusIndex >= 0, "statusIndex must greater than 0 and less than fieldSize");
            this.domainIndex = context.getInteger(Constants.DOMAIN_INDEX, Integer.valueOf(Constants.DOMAIN_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.domainIndex >= 0, "domainIndex must greater than 0 and less than fieldSize");
            this.requestIndex = context.getInteger(Constants.REQUEST_INDEX, Integer.valueOf(Constants.REQUEST_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.requestIndex >= 0, "requestIndex must greater than 0 and less than fieldSize");
            this.requestTimeIndex = context.getInteger(Constants.REQUEST_TIME_INDEX, Integer.valueOf(Constants.REQUEST_TIME_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.requestTimeIndex >= 0, "requestTimeIndex must greater than 0 and less than fieldSize");
            this.ipIndex = context.getInteger(Constants.IP_INDEX, Integer.valueOf(Constants.IP_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.ipIndex >= 0, "ipIndex must greater than 0 and less than fieldSize");
            this.refererIndex = context.getInteger(Constants.REFERER_INDEX, Integer.valueOf(Constants.REFERER_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.refererIndex >= 0, "refererIndex must greater than 0 and less than fieldSize");
            this.userIndex = context.getInteger(Constants.USER_INDEX, Integer.valueOf(Constants.USER_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.userIndex >= 0, "userIndex must greater than 0 and less than fieldSize");
            this.sidIndex = context.getInteger(Constants.SID_INDEX, Integer.valueOf(Constants.SID_INDEX_DFLT)).intValue();
            Preconditions.checkArgument(this.sidIndex >= 0, "sidIndex must greater than 0 and less than fieldSize");

            // 配置静态资源
            configStaticList(context);
            try {
                InetAddress addr = InetAddress.getLocalHost();
                if (this.useIP) {
                    this.host = addr.getHostAddress();
                } else {
                    this.host = addr.getCanonicalHostName();
                }
            } catch (UnknownHostException e) {
                IuniLogInterceptor.logger.warn("Could not get local host address. Exception follows.", e);
            }
            this.adIdFlag = context.getString("adId", "AD_ID");
            Preconditions.checkArgument(this.adIdFlag != null, "adidFlag can not be null.");

            String startDateStr = context.getString("startDate");
            if (!org.apache.commons.lang.StringUtils.isBlank(startDateStr)) {
                try {
                    logger.info("startDate: {}", startDateStr);
                    this.startDate = DateUtils.simpleDateStrToDate(startDateStr);
                } catch (IuniDADateFormatException e) {
                    logger.info("start format error: {}", e.getLocalizedMessage());
                    this.startDate = new Date();
                }
            }

            // 配置IP库信息
            configIpLib(context);
        }

        /**
         * 配置IP库
         *
         * @param context
         */
        private void configIpLib(Context context) {
            this.ipLibName = context.getString(Constants.IP_LIB_NAME);
            Preconditions.checkState(this.ipLibName != null, "The parameter ipLibName must be specified");
            this.ipLibConf = context.getString(Constants.IP_LIB_CONF);
            Preconditions.checkState(this.ipLibConf != null, "The parameter ipLibConf must be specified");
//            EventBus eventBus = new EventBus(this.ipLibName + "-event-bus");
//
//            File configurationFile = new File(ipLibConf);
//            PollingPropertiesFileConfigurationProvider configurationProvider =
//                    new PollingPropertiesFileConfigurationProvider(this.ipLibName, configurationFile, eventBus, 30);
//
//            List<LifecycleAware> components = Lists.newArrayList();
//            components.add(configurationProvider);
//            this.components = components;
//            eventBus.register(this);
        }

        /**
         * 从配置文件中读取静态资源
         *
         * @param context
         */
        private void configStaticList(Context context) {
            String staticResStr = context.getString("staticRes", "").trim();
            IuniLogInterceptor.logger.info("staticResStr: {}", staticResStr);
            if (!IuniStringUtils.isEmpty(staticResStr)) {
                String[] staticResNames = staticResStr.split("\\s+");
                if (staticResNames.length == 0) {
                    return;
                }
                this.staticResList = Lists.newArrayListWithCapacity(staticResNames.length);
                Context staticResContexts = new Context(context.getSubProperties("staticRes."));
                for (String staticResName : staticResNames) {
                    Context staticResContext = new Context(staticResContexts.getSubProperties(staticResName + "."));
                    String specialUrlStr = staticResContext.getString("specialUrl", "");
                    IuniLogInterceptor.logger.info("staticRes {}'s specialUrl : {}", staticResName, specialUrlStr);

                    String[] specialUrls = specialUrlStr.split("\\s+");
                    List<String> specialUrlList = Lists.newArrayListWithCapacity(specialUrls.length);
                    for (String specialUrl : specialUrls) {
                        specialUrlList.add(specialUrl);
                    }
                    this.staticResList.add(new IuniLogInterceptor.StaticResSerializer(staticResName, specialUrlList));
                }
            }
        }

        public Interceptor build() {
            IuniLogInterceptor iuniLogInterceptor = null;
            try {
                iuniLogInterceptor = new IuniLogInterceptor(this.statusIndex, this.requestIndex, this.domainIndex,
                        this.requestTimeIndex, this.ipIndex, this.refererIndex, this.userIndex, this.sidIndex,
                        this.adIdFlag, this.staticResList, this.host, this.startDate,
                        this.ipLibName, this.ipLibConf);
                iuniLogInterceptor.initialize();
            } catch (Exception e) {
                IuniLogInterceptor.logger.error("Build IuniLogInterceptor failed. ", e);
            }
            return iuniLogInterceptor;
        }
    }

    static class StaticResSerializer {
        private final String staticResName;
        private final List<String> specialUrlList;

        public StaticResSerializer(String staticRes, List<String> specialUrl) {
            this.staticResName = staticRes;
            this.specialUrlList = specialUrl;
        }
    }

    public static class Constants {
        public static final String USE_IP = "useIP";
        public static final String HOST = "host";
        public static final boolean USE_IP_DFLT = true;
        public static final String REQUEST_TIME_INDEX = "requestTimeIndex";
        public static final int REQUEST_TIME_INDEX_DFLT = 3;
        public static final String IP_INDEX = "ipIndex";
        public static final int IP_INDEX_DFLT = 4;
        public static final String DOMAIN_INDEX = "domainIndex";
        public static final int DOMAIN_INDEX_DFLT = 6;
        public static final String REQUEST_INDEX = "requestIndex";
        public static final int REQUEST_INDEX_DFLT = 7;
        public static final String STATUS_INDEX = "statusIndex";
        public static final int STATUS_INDEX_DFLT = 8;
        public static final String REFERER_INDEX = "refererIndex";
        public static final int REFERER_INDEX_DFLT = 12;
        public static final String USER_INDEX = "userIndex";
        public static final int USER_INDEX_DFLT = 18;
        public static final String SID_INDEX = "sidIndex";
        public static final int SID_INDEX_DFLT = 19;
        public static final String SUFFIX_USABLE = "suffixUsable";
        public static final String SUFFIX_USEFULL = "usefull";
        public static final String SUFFIX_USELESS = "useless";
        public static final String IP_LIB_NAME = "ipLibName";
        public static final String IP_LIB_CONF = "ipLibConf";
    }
}
