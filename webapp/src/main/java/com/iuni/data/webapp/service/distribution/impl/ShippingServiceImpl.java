package com.iuni.data.webapp.service.distribution.impl;

import com.iuni.data.persist.mapper.distribution.ShippingMapper;
import com.iuni.data.persist.model.distribution.FreightQueryDto;
import com.iuni.data.persist.model.distribution.FreightTableDto;
import com.iuni.data.webapp.service.distribution.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("shippingServiceOfDistribution")
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 运费 - 正向
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<FreightTableDto> selectFreightOfForward(FreightQueryDto queryDto) {
        return shippingMapper.selectFreightOfForward(queryDto);
    }

    /**
     * 运费 - 逆向
     * @param queryDto
     * @return
     */
    @Override
    public List<FreightTableDto> selectFreightOfReverse(FreightQueryDto queryDto) {
        return shippingMapper.selectFreightOfReverse(queryDto);
    }

}
