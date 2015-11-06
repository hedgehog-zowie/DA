package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.StockMapper;
import com.iuni.data.persist.model.financial.*;
import com.iuni.data.webapp.service.financial.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("stockServiceOfFinancial")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<StockDetailsTableDto> selectStockDetails(StockDetailsQueryDto queryDto) {
        return stockMapper.selectStockDetails(queryDto);
    }

    @Override
    public List<StockMoveDetailsTableDto> selectStockMoveDetails(StockMoveDetailsQueryDto queryDto) {
        return stockMapper.selectStockMoveDetails(queryDto);
    }

    @Override
    public List<ProcurementDetailsTableDto> selectProcurementDetails(ProcurementDetailsQueryDto queryDto) {
        return stockMapper.selectProcurementDetails(queryDto);
    }
}
