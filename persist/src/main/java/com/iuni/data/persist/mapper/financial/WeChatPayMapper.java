package com.iuni.data.persist.mapper.financial;

import com.iuni.data.persist.model.financial.WeChatPayQueryDto;
import com.iuni.data.persist.model.financial.WeChatPayTableDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("weChatPayMapperOfFinancial")
public interface WeChatPayMapper {

    /**
     * 微信支付
     * @param queryDto
     * @return
     */
    List<WeChatPayTableDto> selectWeChatPay(WeChatPayQueryDto queryDto);

}
