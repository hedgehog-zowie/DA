package com.iuni.data.alipay;

import com.iuni.data.utils.CryptUtils;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.common.TType;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.AlipayUserAccountreportGetRequest;
import com.taobao.api.request.AlipayUserTradeSearchRequest;
import com.taobao.api.response.AlipayUserAccountreportGetResponse;
import com.taobao.api.response.AlipayUserTradeSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TopService {

    private static Logger logger = LoggerFactory.getLogger(TopService.class);

    /**
     * taobao api地址
     */
    private String url;
    /**
     * taobao appKey
     */
    private String appKey;
    /**
     * taobao appSecret
     */
    private String appSecret;
    /**
     * taobao sessionKey
     */
    private String sessionKey;

    /**
     * 加密密钥
     */
    private String secret;

    /**
     * 接收结果接口地址
     */
    private String resultUrl;
    /**
     * 超时
     */
    private int resultUrlConnectionTimeOut;
    private int resultUrlReadTimeOut;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public int getResultUrlConnectionTimeOut() {
        return resultUrlConnectionTimeOut;
    }

    public void setResultUrlConnectionTimeOut(int resultUrlConnectionTimeOut) {
        this.resultUrlConnectionTimeOut = resultUrlConnectionTimeOut;
    }

    public int getResultUrlReadTimeOut() {
        return resultUrlReadTimeOut;
    }

    public void setResultUrlReadTimeOut(int resultUrlReadTimeOut) {
        this.resultUrlReadTimeOut = resultUrlReadTimeOut;
    }

    /**
     * 获取支付宝数据
     *
     * @param startDate
     * @param endDate
     */
    public void getPayData(Date startDate, Date endDate) {
        TaobaoClient client = new DefaultTaobaoClient(getUrl(), getAppKey(), getAppSecret());
        Map<Date, Date> timeRange = DateUtils.parseTimeRange(startDate, endDate, TType.DAY);
        for (Map.Entry<Date, Date> entry : timeRange.entrySet()) {
            getAliTradeRecord(client, entry.getKey(), entry.getValue());
            getAliReportRecord(client, entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取支付宝交易记录
     *
     * @param startDate
     * @param endDate
     */
    private void getAliTradeRecord(TaobaoClient client, Date startDate, Date endDate) {
        logger.info("fetch alipay trade data [{} - {}]", startDate, endDate);
        try {
            Long totalPages = 0L;
            int curPage = 1;
            do {
                AlipayUserTradeSearchResponse response = getAliTradeRecord(client, startDate, endDate, String.valueOf(curPage), "100");
                if (totalPages == 0L) {
                    totalPages = Long.parseLong(response.getTotalPages());
                    logger.info("fetch alipay trade data, totalPages: {}, totalResults: {}.", response.getTotalPages(), response.getTotalResults());
                }
                String data = response.getBody();
                if (data == null)
                    logger.warn("response data is null. start: {}, end: {}", startDate, endDate);
                else
                    postData(getResultUrl() + "/trade", data);
                curPage++;
            } while (curPage <= totalPages);
        } catch (ApiException e) {
            logger.error("fetch alipay trade data error. {}", e.getMessage());
        } catch (Exception e) {
            logger.error("fetch alipay trade data error. {}", e.getMessage());
        }
    }

    /**
     * 获取支付宝对账单记录
     *
     * @param startDate
     * @param endDate
     */
    private void getAliReportRecord(TaobaoClient client, Date startDate, Date endDate) {
        logger.info("fetch alipay report data [{} - {}]", startDate, endDate);
        try {
            Long totalPages = 0L;
            int curPage = 1;
            do {
                AlipayUserAccountreportGetResponse response = getAliReportRecord(client, startDate, endDate, curPage, 100);
                if (totalPages == 0L) {
                    totalPages = response.getTotalPages();
                    logger.info("fetch alipay report data, totalPages: {}, totalResults: {}.", response.getTotalPages(), response.getTotalResults());
                }
                String data = response.getBody();
                if (data == null)
                    logger.warn("response data is null. start: {}, end: {}", startDate, endDate);
                else
                    postData(getResultUrl() + "/report", data);
                curPage++;
            } while (curPage <= totalPages);
        } catch (ApiException e) {
            logger.error("fetch alipay report data error. {}", e.getMessage());
        } catch (Exception e) {
            logger.error("fetch alipay report data error. {}", e.getMessage());
        }
    }

    /**
     * @param client
     * @param startDate yyyy-MM-dd HH:mm:ss
     * @param endDate   yyyy-MM-dd HH:mm:ss
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    private AlipayUserTradeSearchResponse getAliTradeRecord(TaobaoClient client, Date startDate, Date endDate, String pageNo, String pageSize) throws ApiException {
        String startDateStr = DateUtils.dateToSimpleDateStr(startDate, "yyyy-MM-dd HH:mm:ss");
        String endDateStr = DateUtils.dateToSimpleDateStr(endDate, "yyyy-MM-dd HH:mm:ss");
        AlipayUserTradeSearchRequest req = new AlipayUserTradeSearchRequest();
        req.setStartTime(startDateStr);
        req.setEndTime(endDateStr);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);
        return client.execute(req, getSessionKey());
    }

    /**
     * @param client
     * @param startDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    private AlipayUserAccountreportGetResponse getAliReportRecord(TaobaoClient client, Date startDate, Date endDate, long pageNo, long pageSize) throws ApiException {
        AlipayUserAccountreportGetRequest req = new AlipayUserAccountreportGetRequest();
        req.setFields("create_time,type,business_type,balance,in_amount,out_amount,alipay_order_no,merchant_order_no,self_user_id,opt_user_id,memo");
        req.setStartTime(startDate);
        req.setEndTime(endDate);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);
        return client.execute(req, getSessionKey());
    }

    private void postData(String url, String data) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("data", CryptUtils.encrypt3DES(data, getSecret()));

        Date date = new Date();
        params.put("code", CryptUtils.encrypt3DES(String.valueOf(date.getTime()), getSecret()));
        params.put("sign", CryptUtils.generateAlipaySign(data, String.valueOf(date.getTime()), getSecret()));

        logger.info("post: {}", WebUtils.doPost(url, params, getResultUrlConnectionTimeOut(), getResultUrlReadTimeOut()));
    }

}
