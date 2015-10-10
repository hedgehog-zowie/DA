package com.iuni.data.webapp.service.flow.impl;

import com.iuni.data.common.Constants;
import com.iuni.data.common.DataType;
import com.iuni.data.common.TType;
import com.iuni.data.persist.domain.config.BuriedPoint;
import com.iuni.data.persist.mapper.flow.FlowOfBuriedPointMapper;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForQueryDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForTableDto;
import com.iuni.data.persist.repository.config.BuriedPointRepository;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.webapp.service.flow.FlowOfBuriedPointService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("flowOfBuriedPointService")
public class FlowOfBuriedPointServiceImpl implements FlowOfBuriedPointService {

    private static final Logger logger = LoggerFactory.getLogger(FlowOfBuriedPointService.class);

    @Autowired
    private FlowOfBuriedPointMapper flowOfBuriedPointMapper;
    @Autowired
    private BuriedPointRepository buriedPointRepository;
    private String hbaseQuorum;
    private String tableName;

    @Override
    public List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPoints(FlowOfBuriedPointForQueryDto buriedPointQueryDto) {
        return flowOfBuriedPointMapper.selectFlowOfBuriedPoints(buriedPointQueryDto);
    }

    @Override
    public List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPointsFromHbase(FlowOfBuriedPointForQueryDto buriedPointForQueryDto) {
        List<FlowOfBuriedPointForTableDto> resultList = new ArrayList<>();
        Configuration hbaseConf = HBaseConfiguration.create();
        hbaseConf.set(Constants.hbaseQuorum, hbaseQuorum);
        HTable table = null;
        try {
            table = new HTable(hbaseConf, tableName);
            Map<Date, Date> timeRange = DateUtils.parseTimeRange(DateUtils.simpleDateStrToDate(buriedPointForQueryDto.getStartDateStr(), "yyyy/MM/dd"),
                    DateUtils.computeStartDate(DateUtils.simpleDateStrToDate(buriedPointForQueryDto.getEndDateStr(), "yyyy/MM/dd"), 1), TType.DAY);
            List<BuriedPoint> buriedPointList = buriedPointRepository.findByStatusAndCancelFlag(1, 0);
            for (Map.Entry<Date, Date> entry : timeRange.entrySet()) {
                String dateStr = DateUtils.dateToSimpleDateStr(entry.getKey(), "yyyyMMdd");
                Result result = table.get(new Get(Bytes.toBytes(dateStr)));
                if (result.isEmpty())
                    continue;
                for (BuriedPoint buriedPoint : buriedPointList) {
                    String qualifier = buriedPoint.getPointFlag().trim();
                    byte[] pv_value = result.getValue(Bytes.toBytes(DataType.CLICK.getName()), Bytes.toBytes("pv-" + qualifier));
                    byte[] uv_value = result.getValue(Bytes.toBytes(DataType.CLICK.getName()), Bytes.toBytes("uv-" + qualifier));
                    byte[] vv_value = result.getValue(Bytes.toBytes(DataType.CLICK.getName()), Bytes.toBytes("vv-" + qualifier));
                    byte[] ip_value = result.getValue(Bytes.toBytes(DataType.CLICK.getName()), Bytes.toBytes("ip-" + qualifier));
                    long pv = pv_value != null ? Bytes.toLong(pv_value) : 0;
                    long uv = uv_value != null ? Bytes.toLong(uv_value) : 0;
                    long vv = vv_value != null ? Bytes.toLong(vv_value) : 0;
                    long ip = ip_value != null ? Bytes.toLong(ip_value) : 0;
                    if (pv == 0 && uv == 0 && vv == 0 && ip == 0)
                        continue;

                    FlowOfBuriedPointForTableDto flowOfBuriedPointForTableDto = new FlowOfBuriedPointForTableDto();
                    flowOfBuriedPointForTableDto.setDay(dateStr);
                    flowOfBuriedPointForTableDto.setBuriedPointInfo(buriedPoint);
                    flowOfBuriedPointForTableDto.setPv(pv);
                    flowOfBuriedPointForTableDto.setUv(uv);
                    flowOfBuriedPointForTableDto.setVv(vv);
                    flowOfBuriedPointForTableDto.setIp(ip);
                    resultList.add(flowOfBuriedPointForTableDto);
                }
            }
        } catch (IOException e) {
            logger.error("get counter error, HTable is: {}, error msg is: {}", tableName, e.getLocalizedMessage());
        } finally {
            if (table != null)
                try {
                    table.close();
                } catch (IOException e) {
                    logger.error("close hbase table {} error, ", tableName, e);
                }
        }
        return resultList;
    }

    public String getHbaseQuorum() {
        return hbaseQuorum;
    }

    public void setHbaseQuorum(String hbaseQuorum) {
        this.hbaseQuorum = hbaseQuorum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
