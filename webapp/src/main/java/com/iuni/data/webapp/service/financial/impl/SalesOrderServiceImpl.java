package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.SalesOrderMapper;
import com.iuni.data.persist.model.financial.*;
import com.iuni.data.webapp.service.financial.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("salesOrderServiceOfFinancial")
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Override
    public List<SalesOrderDetailsTableDto> selectSalesOrderDetails(SalesOrderDetailsQueryDto queryDto) {
        return salesOrderMapper.selectSalesOrderDetails(queryDto);
    }

    @Override
    public List<NoInvoiceSalesDetailsTableDto> selectNoInvoiceSalesDetails(NoInvoiceSalesDetailsQueryDto queryDto) {
        return salesOrderMapper.selectNoInvoiceSalesDetails(queryDto);
    }

    @Override
    public List<PayAmountCheckDetailsTableDto> selectPayAmountCheck(PayAmountCheckDetailsQueryDto queryDto) {
        return salesOrderMapper.selectPayAmountCheck(queryDto);
    }

    @Override
    public List<RebatesDetailsTableDto> selectRebatesDetails(RebatesDetailsQueryDto queryDto) {
        return salesOrderMapper.selectRebatesDetails(queryDto);
    }

    @Override
    public List<BigOrderDetailsTableDto> selectBigOrderDetails(BigOrderDetailsQueryDto queryDto) {
        return salesOrderMapper.selectBigOrderDetails(queryDto);
    }

}
