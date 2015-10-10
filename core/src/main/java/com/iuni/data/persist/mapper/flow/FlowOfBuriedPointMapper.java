package com.iuni.data.persist.mapper.flow;

import com.iuni.data.persist.model.flow.FlowOfBuriedPointForQueryDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface FlowOfBuriedPointMapper {

    List<FlowOfBuriedPointForTableDto> selectFlowOfBuriedPoints(FlowOfBuriedPointForQueryDto buriedPointQueryDto);

}
