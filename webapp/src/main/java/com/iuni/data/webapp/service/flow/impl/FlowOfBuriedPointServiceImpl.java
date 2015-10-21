package com.iuni.data.webapp.service.flow.impl;

import com.iuni.data.common.DataType;
import com.iuni.data.common.TType;
import com.iuni.data.hbase.HBaseOperator;
import com.iuni.data.persist.domain.config.BuriedPoint;
import com.iuni.data.persist.mapper.flow.FlowOfBuriedPointMapper;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForQueryDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForTableDto;
import com.iuni.data.persist.repository.config.BuriedPointRepository;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.webapp.service.flow.FlowOfBuriedPointService;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private HBaseOperator hBaseOperator;

    @Override
    public List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPointsFromOracle(FlowOfBuriedPointForQueryDto buriedPointQueryDto) {
        return flowOfBuriedPointMapper.selectFlowOfBuriedPoints(buriedPointQueryDto);
    }

    @Override
    public List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPointsFromHbase(FlowOfBuriedPointForQueryDto buriedPointForQueryDto) {
        Map<Date, Date> timeRange = DateUtils.parseTimeRange(DateUtils.simpleDateStrToDate(buriedPointForQueryDto.getStartDateStr(), "yyyy/MM/dd"),
                DateUtils.computeStartDate(DateUtils.simpleDateStrToDate(buriedPointForQueryDto.getEndDateStr(), "yyyy/MM/dd"), 1), TType.DAY);

        Set<String> rowKeySet = new HashSet<>();
        for(Map.Entry<Date,Date> entry: timeRange.entrySet())
            rowKeySet.add(DateUtils.dateToSimpleDateStr(entry.getKey(), "yyyyMMdd"));
        Map<String, Result> resultMap = hBaseOperator.getRow(rowKeySet);

        List<BuriedPoint> buriedPointList = buriedPointRepository.findByStatusAndCancelFlag(1, 0);

        byte[] familyClick = Bytes.toBytes(DataType.CLICK.getName());
        List<FlowOfBuriedPointForTableDto> resultList = new ArrayList<>();
        for (Map.Entry<String, Result> entry:resultMap.entrySet()) {
            String dateStr = entry.getKey();
            Result result = entry.getValue();
            if (result.isEmpty())
                continue;
            Map<byte[], byte[]> familyMap = result.getFamilyMap(familyClick);
            for (BuriedPoint buriedPoint : buriedPointList) {
                String qualifier = buriedPoint.getPointFlag().trim();
//                byte[] pv_value =  result.getValue(familyClick, Bytes.toBytes("pv-" + qualifier));
//                byte[] uv_value = result.getValue(familyClick, Bytes.toBytes("uv-" + qualifier));
//                byte[] vv_value = result.getValue(familyClick, Bytes.toBytes("vv-" + qualifier));
//                byte[] ip_value = result.getValue(familyClick, Bytes.toBytes("ip-" + qualifier));
                byte[] pv_value = familyMap.get(Bytes.toBytes("pv-" + qualifier));
                byte[] uv_value = familyMap.get(Bytes.toBytes("uv-" + qualifier));
                byte[] vv_value = familyMap.get(Bytes.toBytes("vv-" + qualifier));
                byte[] ip_value = familyMap.get(Bytes.toBytes("ip-" + qualifier));
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
        return resultList;
    }

}
