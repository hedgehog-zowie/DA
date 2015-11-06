package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractQueryDto;
import org.springframework.util.StringUtils;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class BigOrderDetailsQueryDto extends AbstractQueryDto {

    private String dateRangeStringOfShipping;
    private String startDateStrOfShipping;
    private String endDateStrOfShipping;
    private String dateRangeStringOfPaymentConfirm;
    private String startDateStrOfPaymentConfirm;
    private String endDateStrOfPaymentConfirm;

    public String getDateRangeStringOfShipping() {
        return dateRangeStringOfShipping;
    }

    public void setDateRangeStringOfShipping(String dateRangeStringOfShipping) {
        this.dateRangeStringOfShipping = dateRangeStringOfShipping;
    }

    public String getStartDateStrOfShipping() {
        return startDateStrOfShipping;
    }

    public void setStartDateStrOfShipping(String startDateStrOfShipping) {
        this.startDateStrOfShipping = startDateStrOfShipping;
    }

    public String getEndDateStrOfShipping() {
        return endDateStrOfShipping;
    }

    public void setEndDateStrOfShipping(String endDateStrOfShipping) {
        this.endDateStrOfShipping = endDateStrOfShipping;
    }

    public String getDateRangeStringOfPaymentConfirm() {
        return dateRangeStringOfPaymentConfirm;
    }

    public void setDateRangeStringOfPaymentConfirm(String dateRangeStringOfPaymentConfirm) {
        this.dateRangeStringOfPaymentConfirm = dateRangeStringOfPaymentConfirm;
    }

    public String getStartDateStrOfPaymentConfirm() {
        return startDateStrOfPaymentConfirm;
    }

    public void setStartDateStrOfPaymentConfirm(String startDateStrOfPaymentConfirm) {
        this.startDateStrOfPaymentConfirm = startDateStrOfPaymentConfirm;
    }

    public String getEndDateStrOfPaymentConfirm() {
        return endDateStrOfPaymentConfirm;
    }

    public void setEndDateStrOfPaymentConfirm(String endDateStrOfPaymentConfirm) {
        this.endDateStrOfPaymentConfirm = endDateStrOfPaymentConfirm;
    }

    public void parseDateRangeStringOfShipping() {
        if(!StringUtils.isEmpty(dateRangeStringOfShipping)) {
            String[] strs = dateRangeStringOfShipping.split(dateRangeToStr);
            setStartDateStrOfShipping(strs[0].trim());
            setEndDateStrOfShipping(strs[1].trim());
        }
    }

    public void parseDateRangeStringOfPaymentConfirm() {
        if(!StringUtils.isEmpty(dateRangeStringOfPaymentConfirm)) {
            String[] strs = dateRangeStringOfPaymentConfirm.split(dateRangeToStr);
            setStartDateStrOfPaymentConfirm(strs[0].trim());
            setEndDateStrOfPaymentConfirm(strs[1].trim());
        }
    }

}
