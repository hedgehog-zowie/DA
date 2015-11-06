package com.iuni.data.persist.mapper.distribution;

import com.iuni.data.persist.model.distribution.*;
import com.iuni.data.persist.model.financial.ProcurementDetailsQueryDto;
import com.iuni.data.persist.model.financial.ProcurementDetailsTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("stockMapperOfDistribution")
public interface StockMapper {

    /**
     * 仓库出入库来源汇总 - 每天
     *
     * @param queryDto
     * @return
     */
    List<StockBySourceTableDto> selectStockBySourceOfDay(StockBySourceQueryDto queryDto);

    /**
     * 仓库出入库来源汇总 - 时间段
     *
     * @param queryDto
     * @return
     */
    List<StockBySourceTableDto> selectStockBySourceOfRange(StockBySourceQueryDto queryDto);

    /**
     * 仓库出入库数量汇总 - 每天
     *
     * @param queryDto
     * @return
     */
    List<StockTableDto> selectStockOfDay(StockQueryDto queryDto);

    /**
     * 仓库出入库数量汇总 - 时间段
     *
     * @param queryDto
     * @return
     */
    List<StockTableDto> selectStockOfRange(StockQueryDto queryDto);

    /**
     * 各渠道进退换数量汇总
     *
     * @param queryDto
     * @return
     */
    List<StockByChannelTableDto> selectStockByChannel(StockByChannelQueryDto queryDto);

    /**
     * 销售出库明细 - 物流
     *
     * @param queryDto
     * @return
     */
    List<StockMoveDetailsTableDto> selectStockMoveDetails(StockMoveDetailsQueryDto queryDto);


}
