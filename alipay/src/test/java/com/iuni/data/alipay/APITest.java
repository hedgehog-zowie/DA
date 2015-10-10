package com.iuni.data.alipay;

import com.iuni.data.utils.CryptUtils;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.AlipayRecord;
import com.taobao.api.domain.TradeRecord;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.AlipayUserAccountreportGetRequest;
import com.taobao.api.request.AlipayUserTradeSearchRequest;
import com.taobao.api.response.AlipayUserAccountreportGetResponse;
import com.taobao.api.response.AlipayUserTradeSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class APITest {

    private static Logger logger = LoggerFactory.getLogger(APITest.class);

    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String secret = "123456";
    public static final String tradeResult = "{\"alipay_user_trade_search_response\":{\"total_pages\":49,\"total_results\":483,\"trade_records\":{\"trade_record\":[{\"alipay_order_no\":\"BO20150422523395232502\",\"create_time\":\"2015-04-22 23:37:43\",\"in_out_type\":\"out\",\"merchant_order_no\":\"HJCOM==3200060031==1177511603950919==1029663363085314\",\"modified_time\":\"2015-04-22 23:37:43\",\"opposite_name\":\"*江天猫技术有限公司\",\"opposite_user_id\":\"2088001408364260\",\"order_from\":\"ALIPAY\",\"order_status\":\"ACC_FINISHED\",\"order_title\":\"天猫佣金（类目）{1029663363085314}扣款\",\"order_type\":\"CAE\",\"owner_user_id\":\"2088411239144258\",\"service_charge\":\"0.00\",\"total_amount\":\"39.98\"},{\"alipay_order_no\":\"BO20150422523395202502\",\"create_time\":\"2015-04-22 23:37:43\",\"in_out_type\":\"out\",\"merchant_order_no\":\"CAE_POINT_53792865442\",\"modified_time\":\"2015-04-22 23:37:43\",\"opposite_name\":\"*江天猫技术有限公司\",\"opposite_user_id\":\"2088001674871211\",\"order_from\":\"ALIPAY\",\"order_status\":\"ACC_FINISHED\",\"order_title\":\"代扣返点积分1029663363085314\",\"order_type\":\"CAE\",\"owner_user_id\":\"2088411239144258\",\"service_charge\":\"0.00\",\"total_amount\":\"1.99\"},{\"alipay_order_no\":\"2015042221001001850239353457\",\"create_time\":\"2015-04-22 22:47:08\",\"in_out_type\":\"in\",\"merchant_order_no\":\"T200P1030970336302647\",\"modified_time\":\"2015-04-22 22:47:20\",\"opposite_logon_id\":\"128*@qq.com\",\"opposite_name\":\"*冬平\",\"opposite_user_id\":\"2088902825784850\",\"order_from\":\"TAOBAO\",\"order_status\":\"TRADE_CLOSED\",\"order_title\":\"全国联保 等多件\",\"order_type\":\"TRADE\",\"owner_logon_id\":\"iun*@iuni.com\",\"owner_name\":\"*圳市艾优尼科技有限公司\",\"owner_user_id\":\"2088411239144258\",\"partner_id\":\"PARTNER_TAOBAO_ORDER\",\"service_charge\":\"0.00\",\"total_amount\":\"1099.00\"},{\"alipay_order_no\":\"2015042221001001780242973699\",\"create_time\":\"2015-04-22 22:19:26\",\"in_out_type\":\"in\",\"merchant_order_no\":\"T200P1030947052404810\",\"modified_time\":\"2015-04-25 22:19:41\",\"opposite_logon_id\":\"159****1834\",\"opposite_name\":\"*路明\",\"opposite_user_id\":\"2088702774923784\",\"order_from\":\"TAOBAO\",\"order_status\":\"TRADE_CLOSED\",\"order_title\":\"全国联保 等多件\",\"order_type\":\"TRADE\",\"owner_logon_id\":\"iun*@iuni.com\",\"owner_name\":\"*圳市艾优尼科技有限公司\",\"owner_user_id\":\"2088411239144258\",\"partner_id\":\"PARTNER_TAOBAO_ORDER\",\"service_charge\":\"0.00\",\"total_amount\":\"1099.00\"},{\"alipay_order_no\":\"2015042221001001250203643026\",\"create_time\":\"2015-04-22 21:44:02\",\"in_out_type\":\"in\",\"merchant_order_no\":\"T200P929353499712391\",\"modified_time\":\"2015-04-23 10:33:41\",\"opposite_logon_id\":\"547*@qq.com\",\"opposite_name\":\"*泽凯\",\"opposite_user_id\":\"2088102957935252\",\"order_from\":\"TAOBAO\",\"order_status\":\"WAIT_BUYER_CONFIRM_GOODS\",\"order_title\":\"全国联保 等多件\",\"order_type\":\"TRADE\",\"owner_logon_id\":\"iun*@iuni.com\",\"owner_name\":\"*圳市艾优尼科技有限公司\",\"owner_user_id\":\"2088411239144258\",\"partner_id\":\"PARTNER_TAOBAO_ORDER\",\"service_charge\":\"0.00\",\"total_amount\":\"2399.00\"},{\"alipay_order_no\":\"2015042221001001660239272700\",\"create_time\":\"2015-04-22 21:38:57\",\"in_out_type\":\"in\",\"merchant_order_no\":\"T200P929886367020464\",\"modified_time\":\"2015-04-22 21:40:43\",\"opposite_logon_id\":\"783*@qq.com\",\"opposite_name\":\"*鹤\",\"opposite_user_id\":\"2088002460901660\",\"order_from\":\"TAOBAO\",\"order_status\":\"TRADE_CLOSED\",\"order_title\":\"全国联保 等多件\",\"order_type\":\"TRADE\",\"owner_logon_id\":\"iun*@iuni.com\",\"owner_name\":\"*圳市艾优尼科技有限公司\",\"owner_user_id\":\"2088411239144258\",\"partner_id\":\"PARTNER_TAOBAO_ORDER\",\"service_charge\":\"0.00\",\"total_amount\":\"1879.00\"},{\"alipay_order_no\":\"2015042221001001810238293677\",\"create_time\":\"2015-04-22 21:38:27\",\"in_out_type\":\"in\",\"merchant_order_no\":\"T200P929884769913550\",\"modified_time\":\"2015-04-25 21:38:41\",\"opposite_logon_id\":\"135****1511\",\"opposite_name\":\"*英\",\"opposite_user_id\":\"2088802303822819\",\"order_from\":\"TAOBAO\",\"order_status\":\"TRADE_CLOSED\",\"order_title\":\"全国联保 等多件\",\"order_type\":\"TRADE\",\"owner_logon_id\":\"iun*@iuni.com\",\"owner_name\":\"*圳市艾优尼科技有限公司\",\"owner_user_id\":\"2088411239144258\",\"partner_id\":\"PARTNER_TAOBAO_ORDER\",\"service_charge\":\"0.00\",\"total_amount\":\"1999.00\"},{\"alipay_order_no\":\"2015042221001001510245466507\",\"create_time\":\"2015-04-22 21:22:15\",\"in_out_type\":\"in\",\"merchant_order_no\":\"T200P1030886013066038\",\"modified_time\":\"2015-04-23 10:33:21\",\"opposite_logon_id\":\"jan*@eyou.com\",\"opposite_name\":\"*建颖\",\"opposite_user_id\":\"2088502478439514\",\"order_from\":\"TAOBAO\",\"order_status\":\"WAIT_BUYER_CONFIRM_GOODS\",\"order_title\":\"全国联保 等多件\",\"order_type\":\"TRADE\",\"owner_logon_id\":\"iun*@iuni.com\",\"owner_name\":\"*圳市艾优尼科技有限公司\",\"owner_user_id\":\"2088411239144258\",\"partner_id\":\"PARTNER_TAOBAO_ORDER\",\"service_charge\":\"0.00\",\"total_amount\":\"1099.00\"},{\"alipay_order_no\":\"BO20150422522278532502\",\"create_time\":\"2015-04-22 21:20:27\",\"in_out_type\":\"out\",\"merchant_order_no\":\"HJCOM==3200060031==1177485903400919==927107480114190\",\"modified_time\":\"2015-04-22 21:20:27\",\"opposite_name\":\"*江天猫技术有限公司\",\"opposite_user_id\":\"2088001408364260\",\"order_from\":\"ALIPAY\",\"order_status\":\"ACC_FINISHED\",\"order_title\":\"天猫佣金（类目）{927107480114190}扣款\",\"order_type\":\"CAE\",\"owner_user_id\":\"2088411239144258\",\"service_charge\":\"0.00\",\"total_amount\":\"36.00\"},{\"alipay_order_no\":\"BO20150422522278522502\",\"create_time\":\"2015-04-22 21:20:27\",\"in_out_type\":\"out\",\"merchant_order_no\":\"CAE_POINT_90127078230\",\"modified_time\":\"2015-04-22 21:20:27\",\"opposite_name\":\"*江天猫技术有限公司\",\"opposite_user_id\":\"2088001674871211\",\"order_from\":\"ALIPAY\",\"order_status\":\"ACC_FINISHED\",\"order_title\":\"代扣返点积分927107480114190\",\"order_type\":\"CAE\",\"owner_user_id\":\"2088411239144258\",\"service_charge\":\"0.00\",\"total_amount\":\"1.80\"}]}},\"sign\":\"MC0CFQCXCelhBhOBvqVHVVaqa5KFfIMqhQIUaapdOw/8/xI+ynzkzBA1MuSxgPk=\"}";

    public static final String reportResult = "{\"alipay_user_accountreport_get_response\":{\"alipay_records\":{\"alipay_record\":[{\"alipay_order_no\":\"BO20150421501781922502\",\"balance\":\"1201925.37\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 10:07:10\",\"memo\":\"代扣返点积分921554134418559\",\"merchant_order_no\":\"CAE_POINT_90085511135\",\"opt_user_id\":\"20880016748712110156\",\"out_amount\":\"0.13\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"BO20150421502514162502\",\"balance\":\"1204250.57\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 10:54:10\",\"memo\":\"代扣款（扣款用途：卖家版运费险:YFX-927434937251662）\",\"merchant_order_no\":\"bill_2907384154_YFX-927434937251662\",\"opt_user_id\":\"20880118865362420156\",\"out_amount\":\"0.40\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"2015041021001001570219660662\",\"balance\":\"1209966.05\",\"business_type\":\"transfer_01\",\"create_time\":\"2015-04-21 11:17:46\",\"in_amount\":\"1999.00\",\"merchant_order_no\":\"T200P1020827611724903\",\"opt_user_id\":\"20880063000888880156\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"BO20150421502864072502\",\"balance\":\"1209924.08\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 11:17:47\",\"memo\":\"天猫佣金（类目）{1020827611724903}扣款\",\"merchant_order_no\":\"HJCOM==3200060031==1177079343230919==1020827611724903\",\"opt_user_id\":\"20880014083642600156\",\"out_amount\":\"39.98\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"BO20150421502867202502\",\"balance\":\"1211921.09\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 11:18:01\",\"memo\":\"代扣返点积分917904233738391\",\"merchant_order_no\":\"CAE_POINT_90185268560\",\"opt_user_id\":\"20880016748712110156\",\"out_amount\":\"1.99\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"2015042114585692**76\",\"balance\":\"1215638.14\",\"business_type\":\"other\",\"create_time\":\"2015-04-21 11:44:33\",\"in_amount\":\"1800.00\",\"memo\":\"分期购交易号[2015041521001001830231191009]\",\"merchant_order_no\":\"T200P922661746382286\",\"opt_user_id\":\"dummy\",\"self_user_id\":\"20884112391442580156\",\"type\":\"deposit\"},{\"alipay_order_no\":\"2015033021001001090209486658\",\"balance\":\"1214177.44\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 14:04:05\",\"memo\":\"售后维权[T200P908029515628982]\",\"merchant_order_no\":\"T200P908029515628982\",\"opt_user_id\":\"20880126576140950156\",\"out_amount\":\"5.00\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"BO20150421518924562602\",\"balance\":\"1214162.99\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 14:04:42\",\"in_amount\":\"0.10\",\"memo\":\"天猫佣金（类目）{909888927939390}扣款\",\"merchant_order_no\":\"HJCOM==3200060031==1177122687420919==909888927939390\",\"opt_user_id\":\"20880014083642600156\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"},{\"alipay_order_no\":\"2015041921001001420240236686\",\"balance\":\"1215963.04\",\"business_type\":\"payment_02\",\"create_time\":\"2015-04-21 14:52:12\",\"in_amount\":\"1800.00\",\"memo\":\"花呗交易号[2015041921001001420240236686]\",\"merchant_order_no\":\"T200P924788553992566\",\"opt_user_id\":\"20882022078734230156\",\"self_user_id\":\"20884112391442580156\",\"type\":\"payment\"},{\"alipay_order_no\":\"BO20150421506033722502\",\"balance\":\"1229667.01\",\"business_type\":\"transfer_02\",\"create_time\":\"2015-04-21 15:19:50\",\"memo\":\"代扣返点积分1019509709710209\",\"merchant_order_no\":\"CAE_POINT_53461643946\",\"opt_user_id\":\"20880016748712110156\",\"out_amount\":\"1.99\",\"self_user_id\":\"20884112391442580156\",\"type\":\"transfer\"}]},\"total_pages\":17,\"total_results\":165}}";

    protected static String httpsUrl = "https://eco.taobao.com/router/rest";
    protected static String appKey = "21796054";
    protected static String appSecret = "e1aa2b4a59c76dc461444d6ee535f8ee";
    protected static String sessionKey = "61023171d3432ee217e771b0d105026655b484185a699f12091235919";

    public static void testAliTrade() throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(httpsUrl, appKey, appSecret);
        String totalPages, totalResults;
        AlipayUserTradeSearchRequest req = new AlipayUserTradeSearchRequest();
        req.setStartTime("2015-04-20 00:00:00");
        req.setEndTime("2015-04-21 00:00:00");
        req.setPageSize("500");
        int curPage = 1;
        do {
            req.setPageNo(String.valueOf(curPage));
            AlipayUserTradeSearchResponse response = client.execute(req, sessionKey);
            totalPages = response.getTotalPages();
            totalResults = response.getTotalResults();
            logger.info("total pages: {}", totalPages);
            logger.info("total results: {}", totalResults);
            response = client.execute(req, sessionKey);
            List<TradeRecord> tradeRecordList = response.getTradeRecords();
            for (TradeRecord tradeRecord : tradeRecordList) {
                logger.info("{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}",
                        tradeRecord.getAlipayOrderNo(), tradeRecord.getMerchantOrderNo(), tradeRecord.getOrderType(),
                        tradeRecord.getOrderStatus(), tradeRecord.getOwnerUserId(), tradeRecord.getOwnerLogonId(),
                        tradeRecord.getOwnerName(), tradeRecord.getOppositeUserId(), tradeRecord.getOppositeName(),
                        tradeRecord.getOppositeLogonId(), tradeRecord.getOrderTitle(), tradeRecord.getTotalAmount(),
                        tradeRecord.getServiceCharge(), tradeRecord.getOrderFrom(), tradeRecord.getModifiedTime(),
                        tradeRecord.getCreateTime(), tradeRecord.getInOutType());
            }
            testApi("http://localhost:8080/ws/alipay/report/trade", response.getBody());
            curPage++;
        } while (curPage <= Integer.parseInt(totalPages));
    }

    public static void testAliReport() throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(httpsUrl, appKey, appSecret);
        Long totalPages, totalResults;
        AlipayUserAccountreportGetRequest req = new AlipayUserAccountreportGetRequest();
        req.setFields("create_time,type,business_type,balance,in_amount,out_amount,alipay_order_no,merchant_order_no,self_user_id,opt_user_id,memo");
        Date startTime = SimpleDateFormat.getDateTimeInstance().parse("2015-04-21 00:00:00");
        req.setStartTime(startTime);
        Date endTime = SimpleDateFormat.getDateTimeInstance().parse("2015-04-22 00:00:00");
        req.setEndTime(endTime);
        req.setPageSize(500L);
        long curPage = 1L;
        do {
            req.setPageNo(curPage);
            AlipayUserAccountreportGetResponse response = client.execute(req, sessionKey);
            totalPages = response.getTotalPages();
            totalResults = response.getTotalResults();
            logger.info("total pages: {}", totalPages);
            logger.info("total results: {}", totalResults);
            response = client.execute(req, sessionKey);
            List<AlipayRecord> alipayRecordList = response.getAlipayRecords();
            ;
            for (AlipayRecord alipayRecord : alipayRecordList) {
                logger.info("{},{},{},{},{},{},{},{},{},{},{}",
                        alipayRecord.getBalance(), alipayRecord.getMemo(), alipayRecord.getAlipayOrderNo(),
                        alipayRecord.getOptUserId(), alipayRecord.getMerchantOrderNo(), alipayRecord.getCreateTime(),
                        alipayRecord.getSelfUserId(), alipayRecord.getBusinessType(), alipayRecord.getOutAmount(),
                        alipayRecord.getType(), alipayRecord.getInAmount());
            }
            testApi("http://localhost:8080/ws/alipay/report/record", response.getBody());
            curPage++;
        } while (curPage <= totalPages);
    }

    public static void testApi(String url, String data) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("data", CryptUtils.encrypt3DES(data, secret));

        Date date = new Date();
        params.put("code", CryptUtils.encrypt3DES(String.valueOf(date.getTime()), secret));
        params.put("sign", CryptUtils.generateAlipaySign(data, String.valueOf(date.getTime()), secret));

//        logger.info("get: {}", WebUtils.doGet(url, params));
        logger.info("post: {}", WebUtils.doPost(url, params, 3000, 10000));
    }

    public static void main(String[] args) throws Exception {

//        APITest.testAliTrade();
        APITest.testAliReport();

//        APITest.testApi("http://localhost:8080/ws/alipay/trade", tradeResult);
//        APITest.testApi("http://localhost:8080/ws/alipay/report", reportResult);

//        String str = "test中文";
//        logger.info(CryptUtils.decrypt3DES(CryptUtils.encrypt3DES(str, secret), secret));

        // MC0CFQCXCelhBhOBvqVHVVaqa5KFfIMqhQIUaapdOw
        // MC0CFFohSY5/7qbqytkIjpIIK7u9/5cLAhUAiC8/jU68bq3yOSRzdGuLcLF+Ul4=
    }

}
