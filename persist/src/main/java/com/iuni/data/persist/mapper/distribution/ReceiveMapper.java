package com.iuni.data.persist.mapper.distribution;

import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsQueryDto;
import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("receiveMapperOfDistribution")
public interface ReceiveMapper {

    /**
     * 退货明细 - 物流
     * @param queryDto
     * @return
     */
    List<ReturnGoodsDetailsTableDto> selectReturnGoodsDetails(ReturnGoodsDetailsQueryDto queryDto);

}
