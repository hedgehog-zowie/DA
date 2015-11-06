package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.*;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface StockService {

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
