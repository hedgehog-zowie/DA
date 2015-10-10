package com.iuni.data.webapp.service.activity.impl;

import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.QClickWebKpi;
import com.iuni.data.persist.domain.webkpi.QPageWebKpi;
import com.iuni.data.persist.mapper.activity.ActivityChannelMapper;
import com.iuni.data.persist.model.activity.ChannelChartDto;
import com.iuni.data.persist.model.activity.ChannelQueryDto;
import com.iuni.data.persist.model.activity.ChannelTableDto;
import com.iuni.data.persist.repository.webkpi.ClickWebKpiRepository;
import com.iuni.data.persist.repository.webkpi.PageWebKpiRepository;
import com.iuni.data.webapp.service.activity.ActivityService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public List<ChannelTableDto> selectActivityChannel(ChannelQueryDto queryDto) {
        return activityChannelMapper.selectActivityChannel(queryDto);
    }

    @Override
    public List<ChannelChartDto> selectOrderByActivityChannel(ChannelQueryDto queryDto) {
        return activityChannelMapper.selectOrderByActivityChannel(queryDto);
    }

    @Override
    public List<ChannelChartDto> selectPaidOrderByActivityChannel(ChannelQueryDto queryDto) {
        return activityChannelMapper.selectPaidOrderByActivityChannel(queryDto);
    }

    @Override
    public List<ChannelChartDto> selectPVByActivityChannel(ChannelQueryDto queryDto) {
        return activityChannelMapper.selectPVByActivityChannel(queryDto);
    }

    @Override
    public List<ChannelChartDto> selectUVByActivityChannel(ChannelQueryDto queryDto) {
        return activityChannelMapper.selectUVByActivityChannel(queryDto);
    }

    @Override
    public List<ChannelChartDto> selectVVByActivityChannel(ChannelQueryDto queryDto) {
        return activityChannelMapper.selectVVByActivityChannel(queryDto);
    }

}
