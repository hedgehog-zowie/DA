package com.iuni.data.weixin;

import com.iuni.data.common.TType;
import com.iuni.data.persist.domain.weixin.BillDetail;
import com.iuni.data.persist.repository.weixin.BillDetailRepository;
import com.iuni.data.utils.DateUtils;
import com.tencent.business.DownloadBillBusiness;
import com.tencent.common.Configure;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.downloadbill_protocol.DownloadBillResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class WeixinService {

    private static Logger logger = LoggerFactory.getLogger(WeixinService.class);

    private final int billDetailColNums = 24;

    @Autowired
    private BillDetailRepository billDetailRepository;

    /**
     * 商户号
     */
    private String mchId;
    /**
     * app id
     */
    private String appId;
    /**
     * partner_key
     */
    private String key;
    /**
     * 证书路径
     */
    private String certLocalPath;
    private String certPassword;

    private boolean initFlag;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCertLocalPath() {
        return certLocalPath;
    }

    public void setCertLocalPath(String certLocalPath) {
        this.certLocalPath = certLocalPath;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }

    private void initWxConfig() {
        if (initFlag)
            return;
        Configure.setAppID(appId);
        Configure.setMchID(mchId);
        Configure.setKey(key);
        Configure.setCertLocalPath(certLocalPath);
        Configure.setCertPassword(certPassword);
        initFlag = true;
    }

    /**
     * 获取微信支付对账单数据
     *
     * @param startDate
     * @param endDate
     */
    public void getPayData(Date startDate, Date endDate) {
        initWxConfig();
        Map<Date, Date> timeRange = DateUtils.parseTimeRange(startDate, endDate, TType.DAY);
        for (Map.Entry<Date, Date> entry : timeRange.entrySet()) {
            DownloadBillReqData downloadBillReqData = new DownloadBillReqData("", DateUtils.dateToSimpleDateStr(entry.getKey(), "yyyyMMdd"), "ALL");
            try {
                new DownloadBillBusiness().run(downloadBillReqData, new ResultHandler());
            } catch (Exception e) {
                logger.error("download bill error: {}", e.getMessage());
            }
        }
    }

    class ResultHandler implements DownloadBillBusiness.ResultListener {

        @Override
        public void onFailByReturnCodeError(DownloadBillResData downloadBillResData) {
            logger.error(downloadBillResData.toString());
        }

        @Override
        public void onFailByReturnCodeFail(DownloadBillResData downloadBillResData) {
            logger.error(downloadBillResData.toString());
        }

        @Override
        public void onDownloadBillFail(String response) {
            logger.error(response);
        }

        @Override
        public void onDownloadBillSuccess(String response) {
            String[] results = response.split("\r\n");
            List<BillDetail> billDetailList = new ArrayList<>();
            for (int i = 1; i < results.length - 2; i++) {
                String[] billDetailCols = results[i].substring(1).split(",`");
                if (billDetailCols.length != billDetailColNums)
                    continue;
                BillDetail billDetail = new BillDetail(billDetailCols[0], billDetailCols[1], billDetailCols[2], billDetailCols[3], billDetailCols[4], billDetailCols[5],
                        billDetailCols[6], billDetailCols[7], billDetailCols[8], billDetailCols[9], billDetailCols[10], billDetailCols[11],
                        billDetailCols[12], billDetailCols[13], billDetailCols[14], billDetailCols[15], billDetailCols[16], billDetailCols[17],
                        billDetailCols[18], billDetailCols[19], billDetailCols[20], billDetailCols[21], billDetailCols[22], billDetailCols[23]);
                logger.debug(billDetail.toString());
                billDetailList.add(billDetail);
            }
            billDetailRepository.save(billDetailList);
        }
    }

}
