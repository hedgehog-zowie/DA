package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderQueryDto;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderTableDto;
import com.iuni.data.persist.model.financial.NotInWarrantyDetailsQueryDto;
import com.iuni.data.persist.model.financial.NotInWarrantyDetailsTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface AfterSalesService {

    /**
     * 售后次数
     * @param queryDto
     * @return
     */
    List<AfterSalesNumOfOrderTableDto> selectAfterSalesNumOfOrder(AfterSalesNumOfOrderQueryDto queryDto);

    /**
     * 非保修单维修
     * @param queryDto
     * @return
     */
    List<NotInWarrantyDetailsTableDto> selectNotInWarrantyRepairs(NotInWarrantyDetailsQueryDto queryDto);

}
