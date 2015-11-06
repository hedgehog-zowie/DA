package com.iuni.data.persist.mapper.distribution;

import com.iuni.data.persist.model.distribution.FreightQueryDto;
import com.iuni.data.persist.model.distribution.FreightTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("shippingMapperOfDistribution")
public interface ShippingMapper {

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
