package com.iuni.data.persist.mapper.distribution;

import com.iuni.data.persist.model.distribution.PositiveTableDto;
import com.iuni.data.persist.model.distribution.PositiveQueryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("positiveMapperOfDistribution")
public interface PositiveMapper {

    /**
     * 正向订单时效
     *
     * @param queryDto
     * @return
     */
    List<PositiveTableDto> selectPositive(PositiveQueryDto queryDto);

}
