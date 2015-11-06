package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractQueryDto;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockQueryDto extends AbstractQueryDto {

    /**
     * 统计方式
     */
    private String dateStyle;

    public String getDateStyle() {
        return dateStyle;
    }

    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }
}
