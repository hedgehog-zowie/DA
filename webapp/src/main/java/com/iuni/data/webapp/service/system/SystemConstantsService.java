package com.iuni.data.webapp.service.system;

import com.iuni.data.persist.model.system.OrderSourceDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface SystemConstantsService {

    /**
     * 查找所有仓储管理系统订单来源
     *
     * @return
     */
    List<OrderSourceDto> selectAllWMSOrderSource();

    /**
     * 查找所有订单系统订单来源
     *
     * @return
     */
    List<OrderSourceDto> selectAllOMOrderSource();

}
