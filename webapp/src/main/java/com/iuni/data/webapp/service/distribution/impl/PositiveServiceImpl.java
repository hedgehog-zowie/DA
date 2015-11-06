package com.iuni.data.webapp.service.distribution.impl;

import com.iuni.data.persist.mapper.distribution.PositiveMapper;
import com.iuni.data.persist.model.distribution.PositiveQueryDto;
import com.iuni.data.persist.model.distribution.PositiveTableDto;
import com.iuni.data.webapp.service.distribution.PositiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("positiveServiceOfDistribution")
public class PositiveServiceImpl implements PositiveService{

    @Autowired
    private PositiveMapper positiveMapper;

    /**
     * 正向订单时效
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<PositiveTableDto> selectPositive(PositiveQueryDto queryDto){
        return positiveMapper.selectPositive(queryDto);
    }

}
