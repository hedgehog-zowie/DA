package com.iuni.data.webapp.service.distribution.impl;

import com.iuni.data.persist.mapper.distribution.StockMapper;
import com.iuni.data.persist.model.distribution.*;
import com.iuni.data.webapp.service.distribution.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("stockServiceOfDistribution")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    /**
     * 仓库出入库来源汇总 - 每天
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<StockBySourceTableDto> selectStockBySourceOfDay(StockBySourceQueryDto queryDto) {
        return stockMapper.selectStockBySourceOfDay(queryDto);
    }

    /**
     * 仓库出入库来源汇总 - 时间段
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<StockBySourceTableDto> selectStockBySourceOfRange(StockBySourceQueryDto queryDto) {
        return stockMapper.selectStockBySourceOfRange(queryDto);
    }

    /**
     * 仓库出入库数量汇总 - 每天
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<StockTableDto> selectStockOfDay(StockQueryDto queryDto) {
        return stockMapper.selectStockOfDay(queryDto);
    }

    /**
     * 仓库出入库数量汇总 - 时间段
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<StockTableDto> selectStockOfRange(StockQueryDto queryDto) {
        return stockMapper.selectStockOfRange(queryDto);
    }

    /**
     * 各渠道进退换数量汇总
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<StockByChannelTableDto> selectStockByChannel(StockByChannelQueryDto queryDto) {
        return stockMapper.selectStockByChannel(queryDto);
    }

    /**
     * 销售出库明细 - 物流
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<StockMoveDetailsTableDto> selectStockMoveDetails(StockMoveDetailsQueryDto queryDto) {
        return stockMapper.selectStockMoveDetails(queryDto);
    }

}
