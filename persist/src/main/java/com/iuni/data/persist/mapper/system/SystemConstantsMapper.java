package com.iuni.data.persist.mapper.system;

import com.iuni.data.persist.model.system.OrderSourceDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统常量mapper
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Repository("systemConstantsMapper")
public interface SystemConstantsMapper {

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
