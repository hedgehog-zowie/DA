package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("salesOrderMapperOfFinancial")
public interface SalesOrderMapper {

    /**
     * 销售明细
     *
     * @param queryDto
     * @return
     */
    List<SalesOrderDetailsTableDto> selectSalesOrderDetails(SalesOrderDetailsQueryDto queryDto);

    /**
     * 未开票销售明细
     *
     * @param queryDto
     * @return
     */
    List<NoInvoiceSalesDetailsTableDto> selectNoInvoiceSalesDetails(NoInvoiceSalesDetailsQueryDto queryDto);

    /**
     * 收款发货发票金额
     *
     * @param queryDto
     * @return
     */
    List<PayAmountCheckDetailsTableDto> selectPayAmountCheck(PayAmountCheckDetailsQueryDto queryDto);

    /**
     * 返利明细
     *
     * @param queryDto
     * @return
     */
    List<RebatesDetailsTableDto> selectRebatesDetails(RebatesDetailsQueryDto queryDto);

    /**
     * 大客户订单
     * @param queryDto
     * @return
     */
    List<BigOrderDetailsTableDto> selectBigOrderDetails(BigOrderDetailsQueryDto queryDto);

}
