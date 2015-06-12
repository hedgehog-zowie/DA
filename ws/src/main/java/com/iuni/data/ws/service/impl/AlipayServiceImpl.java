package com.iuni.data.ws.service.impl;

import com.iuni.data.common.CryptUtils;
import com.iuni.data.persist.domain.taobao.AlipayRecord;
import com.iuni.data.persist.domain.taobao.TradeRecord;
import com.iuni.data.persist.repository.taobao.AlipayRecordRepository;
import com.iuni.data.persist.repository.taobao.TradeRecordRepository;
import com.iuni.data.ws.service.AlipayService;
import com.taobao.api.ApiException;
import com.taobao.api.internal.parser.json.JsonConverter;
import com.taobao.api.response.AlipayUserAccountreportGetResponse;
import com.taobao.api.response.AlipayUserTradeSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayService.class);

    @Value("${secret}")
    private String secret;

    private JsonConverter jsonConverter;

    @Autowired
    private TradeRecordRepository tradeRecordRepository;
    @Autowired
    private AlipayRecordRepository alipayRecordRepository;

    public AlipayServiceImpl() {
        jsonConverter = new JsonConverter();
    }

    @Transactional
    public boolean handleAlipayTrade(String data) {
        try {
            data = CryptUtils.decrypt3DES(data, secret);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        logger.info("request param data: {}", data);
        AlipayUserTradeSearchResponse alipayUserTradeSearchResponse = null;
        try {
            alipayUserTradeSearchResponse = jsonConverter.toResponse(data, AlipayUserTradeSearchResponse.class);
        } catch (ApiException e) {
            return false;
        }
        List<com.taobao.api.domain.TradeRecord> tradeRecordList = alipayUserTradeSearchResponse.getTradeRecords();
        List<TradeRecord> resultList = new ArrayList<>();
        for (com.taobao.api.domain.TradeRecord tradeRecord : tradeRecordList) {
            TradeRecord nTradeRecord = new TradeRecord();
            resultList.add(nTradeRecord.copy(tradeRecord));
//            tradeRecordRepository.deleteByAlipayOrderNo(nTradeRecord.getAlipayOrderNo(), nTradeRecord.getCreateTime());
            logger.debug("trade record: {}", nTradeRecord.toString());
        }

        tradeRecordRepository.save(resultList);
        return true;
    }

    @Transactional
    public boolean handleAlipayRecord(String data) {
        try {
            data = CryptUtils.decrypt3DES(data, secret);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        logger.info("request param data: {}", data);
        AlipayUserAccountreportGetResponse alipayUserAccountreportGetResponse = null;
        try {
            alipayUserAccountreportGetResponse = jsonConverter.toResponse(data, AlipayUserAccountreportGetResponse.class);
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }
        List<com.taobao.api.domain.AlipayRecord> alipayRecordList = alipayUserAccountreportGetResponse.getAlipayRecords();
        List<AlipayRecord> resultList = new ArrayList<>();
        for (com.taobao.api.domain.AlipayRecord alipayRecord : alipayRecordList) {
            AlipayRecord nAlipayRecord = new AlipayRecord();
            resultList.add(nAlipayRecord.copy(alipayRecord));
//            alipayRecordRepository.deleteByAlipayOrderNo(nAlipayRecord.getAlipayOrderNo(), nAlipayRecord.getCreateTime());
            logger.debug("alipay record: {}", nAlipayRecord.toString());
        }

        alipayRecordRepository.save(resultList);
        return true;
    }

}
