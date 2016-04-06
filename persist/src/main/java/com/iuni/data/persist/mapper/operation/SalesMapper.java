package com.iuni.data.persist.mapper.operation;

import com.iuni.data.persist.model.operation.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("salesMapperOfOperation")
public interface SalesMapper {

    /**
     * 查询商品销售数据
     * @param salesQueryDto
     * @return
     */
    List<SalesTableDto> selectSales(SalesQueryDto salesQueryDto);

    /**
     * 查询商城销售数据
     * @param salesQueryDto
     * @return
     */
    List<MallSalesTableDto> selectMallSales(MallSalesQueryDto salesQueryDto);

    /**
     * 查询订单转化率
     * @param convertRateOfOrderQueryDto
     * @return
     */
    List<ConvertRateOfOrderTableDto> selectConvertRateOfOrder(ConvertRateOfOrderQueryDto convertRateOfOrderQueryDto);

}
