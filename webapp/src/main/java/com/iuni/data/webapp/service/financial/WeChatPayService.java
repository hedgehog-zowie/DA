package com.iuni.data.webapp.service.financial;

import com.iuni.data.persist.model.financial.WeChatPayQueryDto;
import com.iuni.data.persist.model.financial.WeChatPayTableDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface WeChatPayService {

    /**
     * 微信支付
     * @param queryDto
     * @return
     */
    List<WeChatPayTableDto> selectWeChatPay(WeChatPayQueryDto queryDto);

}
