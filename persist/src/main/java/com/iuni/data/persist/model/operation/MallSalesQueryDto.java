package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractQueryDto;

/**
 * 商场销售查询条件
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class MallSalesQueryDto extends AbstractQueryDto {

    /**
     * 订单来源，页面参数
     */
    private String orderSourceStr;

    public String getOrderSourceStr() {
        return orderSourceStr;
    }

    public void setOrderSourceStr(String orderSourceStr) {
        this.orderSourceStr = orderSourceStr;
    }

}
