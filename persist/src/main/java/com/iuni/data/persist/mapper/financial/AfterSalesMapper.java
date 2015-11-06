package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderQueryDto;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderTableDto;
import com.iuni.data.persist.model.financial.NotInWarrantyDetailsQueryDto;
import com.iuni.data.persist.model.financial.NotInWarrantyDetailsTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("afterSalesMapperOfFinancial")
public interface AfterSalesMapper {

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
