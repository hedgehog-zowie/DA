package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractQueryDto;

/**
 * 用户行为查询条件
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class UserBehaviorQueryDto extends AbstractQueryDto {

    /**
     * 登录截止时间
     */
    private String endLoginDateStr;

    public String getEndLoginDateStr() {
        return endLoginDateStr;
    }

    public void setEndLoginDateStr(String endLoginDateStr) {
        this.endLoginDateStr = endLoginDateStr;
    }

}
