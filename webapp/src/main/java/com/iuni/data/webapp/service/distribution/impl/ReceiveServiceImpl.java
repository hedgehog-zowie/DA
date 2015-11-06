package com.iuni.data.webapp.service.distribution.impl;

import com.iuni.data.persist.mapper.distribution.ReceiveMapper;
import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsQueryDto;
import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsTableDto;
import com.iuni.data.webapp.service.distribution.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("receiveServiceOfDistribution")
public class ReceiveServiceImpl implements ReceiveService {

    @Autowired
    private ReceiveMapper receiveMapper;

    /**
     * 退货明细 - 物流
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<ReturnGoodsDetailsTableDto> selectReturnGoodsDetails(ReturnGoodsDetailsQueryDto queryDto) {
        return receiveMapper.selectReturnGoodsDetails(queryDto);
    }

}
