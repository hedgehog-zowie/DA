package com.iuni.data.webapp.service.distribution.impl;

import com.iuni.data.persist.mapper.distribution.ReverseMapper;
import com.iuni.data.persist.model.distribution.*;
import com.iuni.data.webapp.service.distribution.ReverseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("reverseServiceOfDistribution")
public class ReverseServiceImpl implements ReverseService{

    @Autowired
    private ReverseMapper reverseMapper;

    /**
     * 逆向签收 - 退货
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<ReverseSignOfBackTableDto> selectReverseSignOfBack(ReverseSignQueryDto queryDto){
        return reverseMapper.selectReverseSignOfBack(queryDto);
    }

    /**
     * 逆向签收 - 换货
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<ReverseSignOfExchangeTableDto> selectReverseSignOfExchange(ReverseSignQueryDto queryDto){
        return reverseMapper.selectReverseSignOfExchange(queryDto);
    }

    /**
     * 逆向签收 - 维修
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<ReverseSignOfRepairTableDto> selectReverseSignOfRepair(ReverseSignQueryDto queryDto){
        return reverseMapper.selectReverseSignOfRepair(queryDto);
    }

}
