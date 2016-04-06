package com.iuni.data.webapp.service.operation.impl;

import com.iuni.data.common.Constants;
import com.iuni.data.hbase.HBaseOperator;
import com.iuni.data.persist.mapper.operation.SalesMapper;
import com.iuni.data.persist.model.operation.*;
import com.iuni.data.webapp.service.operation.SalesService;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("salesServiceOfOperation")
public class SalesServiceImpl implements SalesService {

    private static final Logger logger = LoggerFactory.getLogger(SalesService.class);
    /**
     * 页面上报的数据类型,pv表示页面浏览数据
     */
    private static final String REPORT_TYPE = "pv";
    /**
     * 总数列限定符
     */
    private static final String QUALIFIER_TOTAL = "total";

    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private HBaseOperator hBaseOperator;

    @Override
    public List<SalesTableDto> selectSales(SalesQueryDto salesQueryDto) {
        return salesMapper.selectSales(salesQueryDto);
    }

    @Override
    public List<MallSalesTableDto> selectMallSales(MallSalesQueryDto mallSalesQueryDto) {
        return salesMapper.selectMallSales(mallSalesQueryDto);
    }

    @Override
    public List<ConvertRateOfOrderTableDto> selectConvertRateOfOrder(ConvertRateOfOrderQueryDto convertRateOfOrderQueryDto) {
        List<ConvertRateOfOrderTableDto> resultList = salesMapper.selectConvertRateOfOrder(convertRateOfOrderQueryDto);
        Map<String, Get> getMap = new HashMap<>();
        for(ConvertRateOfOrderTableDto result: resultList) {
            Get get = new Get(Bytes.toBytes(result.getDate()));
            get.addColumn(Bytes.toBytes(REPORT_TYPE), Bytes.toBytes("pv-" + Constants.hbaseTable_qualifierTotal));
            get.addColumn(Bytes.toBytes(REPORT_TYPE), Bytes.toBytes("uv-" + Constants.hbaseTable_qualifierTotal));
            getMap.put(result.getDate(), get);
        }
        Map<String, Result> resultMap = hBaseOperator.get(getMap);
        for(ConvertRateOfOrderTableDto result: resultList) {
            long pv = Bytes.toLong(resultMap.get(result.getDate()).getValue(Bytes.toBytes(REPORT_TYPE), Bytes.toBytes("pv-" + Constants.hbaseTable_qualifierTotal)));
            long uv = Bytes.toLong(resultMap.get(result.getDate()).getValue(Bytes.toBytes(REPORT_TYPE), Bytes.toBytes("uv-" + Constants.hbaseTable_qualifierTotal)));
            result.setPv(pv);
            result.setUv(uv);
        }
        return resultList;
    }

}
