package com.iuni.data.webapp.service.distribution;

import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsQueryDto;
import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ReceiveService {

    /**
     * 退货明细 - 物流
     * @param queryDto
     * @return
     */
    List<ReturnGoodsDetailsTableDto> selectReturnGoodsDetails(ReturnGoodsDetailsQueryDto queryDto);

}
