package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("stockMapperOfFinancial")
public interface StockMapper {

    /**
     * 库存明细报表
     * @param queryDto
     * @return
     */
    List<StockDetailsTableDto> selectStockDetails(StockDetailsQueryDto queryDto);

    /**
     * 销售出库明细报表
     * @param queryDto
     * @return
     */
    List<StockMoveDetailsTableDto> selectStockMoveDetails(StockMoveDetailsQueryDto queryDto);

    /**
     * 入库明细
     *
     * @param queryDto
     * @return
     */
    List<ProcurementDetailsTableDto> selectProcurementDetails(ProcurementDetailsQueryDto queryDto);

}
