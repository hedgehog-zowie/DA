package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.RefundDetailsQueryDto;
import com.iuni.data.persist.model.financial.RefundDetailsTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("returnGoodsMapperOfFinancial")
public interface ReturnGoodsMapper {

    /**
     * 退款明细
     *
     * @param queryDto
     * @return
     */
    List<RefundDetailsTableDto> selectRefundDetails(RefundDetailsQueryDto queryDto);

}
