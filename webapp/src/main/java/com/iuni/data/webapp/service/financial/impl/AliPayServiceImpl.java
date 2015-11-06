package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.AliPayMapper;
import com.iuni.data.persist.model.financial.AliPayQueryDto;
import com.iuni.data.persist.model.financial.AliPayTableDto;
import com.iuni.data.webapp.service.financial.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("aliPayServiceOfFinancial")
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private AliPayMapper aliPayMapper;

    @Override
    public List<AliPayTableDto> selectAliPay(AliPayQueryDto queryDto) {
        return aliPayMapper.selectAliPay(queryDto);
    }

}
