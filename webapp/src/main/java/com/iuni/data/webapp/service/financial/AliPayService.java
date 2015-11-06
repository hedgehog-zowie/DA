package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderQueryDto;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderTableDto;
import com.iuni.data.persist.model.financial.AliPayQueryDto;
import com.iuni.data.persist.model.financial.AliPayTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface AliPayService {

    /**
     * 支付宝记录
     * @param queryDto
     * @return
     */
    List<AliPayTableDto> selectAliPay(AliPayQueryDto queryDto);

}
