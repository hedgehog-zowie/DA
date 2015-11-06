package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderQueryDto;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderTableDto;
import com.iuni.data.persist.model.financial.AliPayQueryDto;
import com.iuni.data.persist.model.financial.AliPayTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("aliPayMapperOfFinancial")
public interface AliPayMapper {

    /**
     * 支付宝记录
     * @param queryDto
     * @return
     */
    List<AliPayTableDto> selectAliPay(AliPayQueryDto queryDto);

}
