package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.WeChatPayMapper;
import com.iuni.data.persist.model.financial.WeChatPayQueryDto;
import com.iuni.data.persist.model.financial.WeChatPayTableDto;
import com.iuni.data.webapp.service.financial.WeChatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("weChatPayServiceOfFinancial")
public class WeChatPayServiceImpl implements WeChatPayService {

    @Autowired
    private WeChatPayMapper weChatPayMapper;

    @Override
    public List<WeChatPayTableDto> selectWeChatPay(WeChatPayQueryDto queryDto) {
        return weChatPayMapper.selectWeChatPay(queryDto);
    }

}
