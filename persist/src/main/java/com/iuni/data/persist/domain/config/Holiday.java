package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the IUNI_DA_HOLIDAY database table.
 * 
 */
@Entity
@Table(name="IUNI_DA_HOLIDAY")
public class Holiday extends AbstractConfig {

	@Column(name="\"NAME\"")
	private String name;

	@Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

    private Integer yr;
    
    //bi-directional many-to-one association to Holiday
  	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
  	@JoinColumn(name="TYPE_ID")
  	private HolidayType holidayType;
    
    public Holiday() {
    }

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getYr() {
		return yr;
	}

	public void setYr(Integer yr) {
		this.yr = yr;
	}

	public HolidayType getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(HolidayType holidayType) {
		this.holidayType = holidayType;
	}

}