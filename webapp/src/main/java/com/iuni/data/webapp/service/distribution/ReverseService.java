package com.iuni.data.webapp.service.distribution;

import com.iuni.data.persist.model.distribution.*;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ReverseService {

    /**
     * 逆向签收 - 退货
     *
     * @param queryDto
     * @return
     */
    List<ReverseSignOfBackTableDto> selectReverseSignOfBack(ReverseSignQueryDto queryDto);

    /**
     * 逆向签收 - 换货
     *
     * @param queryDto
     * @return
     */
    List<ReverseSignOfExchangeTableDto> selectReverseSignOfExchange(ReverseSignQueryDto queryDto);

    /**
     * 逆向签收 - 维修
     *
     * @param queryDto
     * @return
     */
    List<ReverseSignOfRepairTableDto> selectReverseSignOfRepair(ReverseSignQueryDto queryDto);

}
