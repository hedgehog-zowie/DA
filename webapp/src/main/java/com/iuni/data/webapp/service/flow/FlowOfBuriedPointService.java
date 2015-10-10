package com.iuni.data.webapp.service.flow;

import com.iuni.data.persist.model.flow.FlowOfBuriedPointForQueryDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface FlowOfBuriedPointService {

    List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPoints(FlowOfBuriedPointForQueryDto buriedPointQueryDto);

    List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPointsFromHbase(FlowOfBuriedPointForQueryDto buriedPointForQueryDto);
}
