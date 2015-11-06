package com.iuni.data.webapp.service.distribution;

import com.iuni.data.persist.model.distribution.FreightQueryDto;
import com.iuni.data.persist.model.distribution.FreightTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ShippingService {

    /**
     * 运费 - 正向
     *
     * @param queryDto
     * @return
     */
    List<FreightTableDto> selectFreightOfForward(FreightQueryDto queryDto);

    /**
     * 运费 - 逆向
     *
     * @param queryDto
     * @return
     */
    List<FreightTableDto> selectFreightOfReverse(FreightQueryDto queryDto);
}
