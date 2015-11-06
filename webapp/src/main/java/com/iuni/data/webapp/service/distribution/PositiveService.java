package com.iuni.data.webapp.service.distribution;

import com.iuni.data.persist.model.distribution.PositiveQueryDto;
import com.iuni.data.persist.model.distribution.PositiveTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface PositiveService {

    /**
     * 正向订单时效
     *
     * @param queryDto
     * @return
     */
    List<PositiveTableDto> selectPositive(PositiveQueryDto queryDto);

}
