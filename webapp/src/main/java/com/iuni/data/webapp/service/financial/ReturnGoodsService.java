package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.RefundDetailsQueryDto;
import com.iuni.data.persist.model.financial.RefundDetailsTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ReturnGoodsService {

    /**
     * 退款明细
     *
     * @param queryDto
     * @return
     */
    List<RefundDetailsTableDto> selectRefundDetails(RefundDetailsQueryDto queryDto);

}
