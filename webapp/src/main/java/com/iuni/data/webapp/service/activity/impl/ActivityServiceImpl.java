package com.iuni.data.webapp.service.activity.impl;

import com.iuni.data.persist.mapper.activity.ActivityChannelMapper;
import com.iuni.data.persist.model.activity.ActivityChannelChartDto;
import com.iuni.data.persist.model.activity.ActivityChannelQueryDto;
import com.iuni.data.persist.model.activity.ActivityChannelTableDto;
import com.iuni.data.webapp.service.activity.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityChannelMapper activityChannelMapper;

    @Override
    public List<ActivityChannelTableDto> selectActivityChannel(ActivityChannelQueryDto queryDto) {
        return activityChannelMapper.selectActivityChannel(queryDto);
    }

    @Override
    public List<ActivityChannelChartDto> selectOrderByActivityChannel(ActivityChannelQueryDto queryDto) {
        return activityChannelMapper.selectOrderByActivityChannel(queryDto);
    }

    @Override
    public List<ActivityChannelChartDto> selectPaidOrderByActivityChannel(ActivityChannelQueryDto queryDto) {
        return activityChannelMapper.selectPaidOrderByActivityChannel(queryDto);
    }

    @Override
    public List<ActivityChannelChartDto> selectPVByActivityChannel(ActivityChannelQueryDto queryDto) {
        return activityChannelMapper.selectPVByActivityChannel(queryDto);
    }

    @Override
    public List<ActivityChannelChartDto> selectUVByActivityChannel(ActivityChannelQueryDto queryDto) {
        return activityChannelMapper.selectUVByActivityChannel(queryDto);
    }

    @Override
    public List<ActivityChannelChartDto> selectVVByActivityChannel(ActivityChannelQueryDto queryDto) {
        return activityChannelMapper.selectVVByActivityChannel(queryDto);
    }

}
