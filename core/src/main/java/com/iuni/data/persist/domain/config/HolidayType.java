package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the IUNI_DA_HOLIDAY_TYPE database table.
 */
@Entity
@Table(name = "IUNI_DA_HOLIDAY_TYPE")
public class HolidayType extends AbstractConfig {

    @Column(name = "\"NAME\"")
    private String name;

    //bi-directional many-to-one association to Holiday
    @OneToMany(mappedBy = "holidayType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Holiday> holidays;

    public HolidayType() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

}