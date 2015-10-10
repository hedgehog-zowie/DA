package com.iuni.data.flume;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.iuni.data.exceptions.IuniDADateFormatException;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OS日志过滤器（预处理）
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class IuniOSReportLogInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(IuniOSReportLogInterceptor.class);

    private final String host;
    private final Date startDate;
    private final Date endDate;

    public IuniOSReportLogInterceptor(String host, Date startDate, Date endDate) {
        this.host = host;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void initialize() {
    }

    /**
     * 过滤 及 日志预处理
     *
     * @param event
     * @return
     */
    public Event intercept(Event event) {
        IuniOSLog iuniOSLog = JsonUtils.fromJson(new String(event.getBody(), Charsets.UTF_8), IuniOSLog.class);
        // 检查日志上报时间
        if (this.startDate != null && this.startDate.getTime() > iuniOSLog.getReportTime())
            return null;
        if (this.endDate != null && this.endDate.getTime() < iuniOSLog.getReportTime())
            return null;
        iuniOSLog.setCreateTime(System.currentTimeMillis());

        Map<String, String> headers = event.getHeaders();
        // 添加host信息，表示从哪台服务器收集的日志
        if ((!headers.containsKey(Constants.HOST)) && (this.host != null)) {
            headers.put(Constants.HOST, this.host);
        }
        // 添加时间戳
        headers.put("timestamp", String.valueOf(iuniOSLog.getReportTime()));
        event.setBody(Bytes.toBytes(iuniOSLog.toString()));
        return event;
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
        private String host;
        private Date startDate;
        private Date endDate;

        public void configure(Context context) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                if (this.useIP) {
                    this.host = addr.getHostAddress();
                } else {
                    this.host = addr.getCanonicalHostName();
                }
            } catch (UnknownHostException e) {
                logger.warn("Could not get local host address. Exception follows.", e);
            }
            String startDateStr = context.getString("startDate");
            if (!StringUtils.isBlank(startDateStr)) {
                try {
                    logger.info("startDate: {}", startDateStr);
                    this.startDate = DateUtils.simpleDateStrToDate(startDateStr);
                } catch (IuniDADateFormatException e) {
                    logger.info("start date format error: {}", e.getLocalizedMessage());
                }
            }
            String endDateStr = context.getString("endDate");
            if (!StringUtils.isBlank(endDateStr)) {
                try {
                    logger.info("endDateStr: {}", endDateStr);
                    this.endDate = DateUtils.simpleDateStrToDate(endDateStr);
                } catch (IuniDADateFormatException e) {
                    logger.info("end date format error: {}", e.getLocalizedMessage());
                }
            }
        }

        public Interceptor build() {
            IuniOSReportLogInterceptor interceptor = null;
            try {
                interceptor = new IuniOSReportLogInterceptor(this.host, this.startDate, this.endDate);
                interceptor.initialize();
            } catch (Exception e) {
                IuniOSReportLogInterceptor.logger.error("Build IuniOSLogInterceptor failed. ", e);
            }
            return interceptor;
        }
    }

    class Constants {
        public static final String HOST = "host";
    }

    class IuniOSLog {
        // APP应用名， 必须字段
        private String appName;
        // 发布渠道名，必须字段
        private String channelName;
        // APK版本号，必须字段
        private String apkVersion;
        // IMEI号 ，兼容工程机无IMEI，为非必须字段
        private String imei;
        // APP应用用户注册ID，非必须字段
        private String registerUserId;
        // APP应用启动时间，重要字段，但考虑到APP网络异常，为非必须字段，startupTime使用通用的微秒时间戳；注：缺少该字段会导致无法统计相应的APP应用平均使用时长
        private Long startupTime;
        // APP应用退出时间，重要字段，但考虑到APP网络异常，为非必须字段，shutdownTime使用通用的微秒时间戳；缺少该字段会导致无法统计相应的APP应用平均使用时长
        private Long shutdownTime;
        // APP应用启用状态，重要字段，0 : 用户进入APP应该，1：用户退出APP应用；注：缺少该字段会影响数据统计
        private Integer status;
        // 终端机型，非必须字段
        private String mobileModel;
        // 手机号码， 非必须字段
        private String mobileNumber;
        // 终端分辨率高度，非必须字段
        private Integer modelHeight;
        // 终端分辨率高度，非必须字段
        private Integer modelWidth;
        // 终端网络模式，非必须字段
        private String networkMode;
        // 终端网络IP地址，非必须字段
        private String networkIp;
        // 地址位置 - 国家
        private String locationCountry;
        // 地址位置 - 省
        private String locationProvince;
        // 地址位置 - 市
        private String locationCity;
        // 时区？
        private Long locationTime;
        // 日志上报时间
        private Long reportTime;
        // 日志保存时间
        private Long createTime;
        // 启动时间？
        private Long bootTime;
        // 进度？
        private Long duration;
        // app数量？
        private Integer appNum;

        private static final String separator = "\001";

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getApkVersion() {
            return apkVersion;
        }

        public void setApkVersion(String apkVersion) {
            this.apkVersion = apkVersion;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getRegisterUserId() {
            return registerUserId;
        }

        public void setRegisterUserId(String registerUserId) {
            this.registerUserId = registerUserId;
        }

        public Long getStartupTime() {
            return startupTime;
        }

        public void setStartupTime(Long startupTime) {
            this.startupTime = startupTime;
        }

        public Long getShutdownTime() {
            return shutdownTime;
        }

        public void setShutdownTime(Long shutdownTime) {
            this.shutdownTime = shutdownTime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMobileModel() {
            return mobileModel;
        }

        public void setMobileModel(String mobileModel) {
            this.mobileModel = mobileModel;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public Integer getModelHeight() {
            return modelHeight;
        }

        public void setModelHeight(Integer modelHeight) {
            this.modelHeight = modelHeight;
        }

        public Integer getModelWidth() {
            return modelWidth;
        }

        public void setModelWidth(Integer modelWidth) {
            this.modelWidth = modelWidth;
        }

        public String getNetworkMode() {
            return networkMode;
        }

        public void setNetworkMode(String networkMode) {
            this.networkMode = networkMode;
        }

        public String getNetworkIp() {
            return networkIp;
        }

        public void setNetworkIp(String networkIp) {
            this.networkIp = networkIp;
        }

        public String getLocationCountry() {
            return locationCountry;
        }

        public void setLocationCountry(String locationCountry) {
            this.locationCountry = locationCountry;
        }

        public String getLocationProvince() {
            return locationProvince;
        }

        public void setLocationProvince(String locationProvince) {
            this.locationProvince = locationProvince;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public Long getLocationTime() {
            return locationTime;
        }

        public void setLocationTime(Long locationTime) {
            this.locationTime = locationTime;
        }

        public Long getReportTime() {
            return reportTime;
        }

        public void setReportTime(Long reportTime) {
            this.reportTime = reportTime;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Long getBootTime() {
            return bootTime;
        }

        public void setBootTime(Long bootTime) {
            this.bootTime = bootTime;
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public Integer getAppNum() {
            return appNum;
        }

        public void setAppNum(Integer appNum) {
            this.appNum = appNum;
        }

        @Override
        public String toString() {
            return appName + separator
                    + channelName + separator
                    + apkVersion + separator
                    + imei + separator
                    + registerUserId + separator
                    + startupTime + separator
                    + shutdownTime + separator
                    + status + separator
                    + mobileModel + separator
                    + mobileNumber + separator
                    + modelHeight + separator
                    + modelWidth + separator
                    + networkMode + separator
                    + networkIp + separator
                    + locationCountry + separator
                    + locationProvince + separator
                    + locationCity + separator
                    + locationTime + separator
                    + reportTime + separator
                    + createTime + separator
                    + bootTime + separator
                    + duration + separator
                    + appNum;
        }

    }

}
