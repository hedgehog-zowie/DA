package com.iuni.data.persist.mapper.activity;

import com.iuni.data.persist.model.activity.ChannelTableDto;
import com.iuni.data.persist.model.activity.ChannelQueryDto;
import com.iuni.data.persist.model.activity.ChannelChartDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ActivityChannelMapper {

    List<ChannelTableDto> selectActivityChannel(ChannelQueryDto queryDto);

    List<ChannelChartDto> selectOrderByActivityChannel(ChannelQueryDto queryDto);

    List<ChannelChartDto> selectPaidOrderByActivityChannel(ChannelQueryDto queryDto);

    List<ChannelChartDto> selectPVByActivityChannel(ChannelQueryDto queryDto);

    List<ChannelChartDto> selectUVByActivityChannel(ChannelQueryDto queryDto);

    List<ChannelChartDto> selectVVByActivityChannel(ChannelQueryDto queryDto);

}
