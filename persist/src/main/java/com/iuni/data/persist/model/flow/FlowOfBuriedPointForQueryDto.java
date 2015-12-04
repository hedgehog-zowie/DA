package com.iuni.data.persist.model.flow;

import com.iuni.data.persist.model.AbstractQueryDto;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class FlowOfBuriedPointForQueryDto extends AbstractQueryDto{

    private Long buriedGroupId;

    public Long getBuriedGroupId() {
        return buriedGroupId;
    }

    public void setBuriedGroupId(Long buriedGroupId) {
        this.buriedGroupId = buriedGroupId;
    }

}
