package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.ReturnGoodsMapper;
import com.iuni.data.persist.model.financial.RefundDetailsQueryDto;
import com.iuni.data.persist.model.financial.RefundDetailsTableDto;
import com.iuni.data.webapp.service.financial.ReturnGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("returnGoodsServiceOfFinancial")
public class ReturnGoodsServiceImpl implements ReturnGoodsService {

    @Autowired
    private ReturnGoodsMapper returnGoodsMapper;

    @Override
    public List<RefundDetailsTableDto> selectRefundDetails(RefundDetailsQueryDto queryDto) {
        return returnGoodsMapper.selectRefundDetails(queryDto);
    }

}
