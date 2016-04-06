package com.iuni.data.webapp.service.operation;

import com.iuni.data.persist.model.operation.*;

import java.util.List;

/**
 * 销售相关数据查询
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface SalesService {

    /**
     * 商品销售数据查询
     *
     * @param salesQueryDto
     * @return
     */
    List<SalesTableDto> selectSales(SalesQueryDto salesQueryDto);

    /**
     * 商城销售数据查询
     * @param mallSalesQueryDto
     * @return
     */
    List<MallSalesTableDto> selectMallSales(MallSalesQueryDto mallSalesQueryDto);

    /**
     * 订单转化率数据查询
     * @param convertRateOfOrderQueryDto
     * @return
     */
    List<ConvertRateOfOrderTableDto> selectConvertRateOfOrder(ConvertRateOfOrderQueryDto convertRateOfOrderQueryDto);

}
