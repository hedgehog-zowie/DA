package com.iuni.data.webapp.service.common;

import java.util.Date;

public interface HolidayService {
	
	public boolean isHoliday(Date date);
	
	public boolean isWeekDay(Date date);
}
