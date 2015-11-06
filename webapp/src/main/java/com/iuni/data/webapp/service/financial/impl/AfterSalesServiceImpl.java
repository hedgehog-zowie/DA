package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.AfterSalesMapper;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderQueryDto;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderTableDto;
import com.iuni.data.persist.model.financial.NotInWarrantyDetailsQueryDto;
import com.iuni.data.persist.model.financial.NotInWarrantyDetailsTableDto;
import com.iuni.data.webapp.service.financial.AfterSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("afterSalesServiceOfFinancial")
public class AfterSalesServiceImpl implements AfterSalesService {

    @Autowired
    private AfterSalesMapper afterSalesMapper;

    @Override
    public List<AfterSalesNumOfOrderTableDto> selectAfterSalesNumOfOrder(AfterSalesNumOfOrderQueryDto queryDto) {
        return afterSalesMapper.selectAfterSalesNumOfOrder(queryDto);
    }

    @Override
    public List<NotInWarrantyDetailsTableDto> selectNotInWarrantyRepairs(NotInWarrantyDetailsQueryDto queryDto) {
        return afterSalesMapper.selectNotInWarrantyRepairs(queryDto);
    }

}
