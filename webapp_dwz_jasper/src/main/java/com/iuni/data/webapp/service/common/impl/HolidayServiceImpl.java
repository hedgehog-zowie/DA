package com.iuni.data.webapp.service.common.impl;

import java.util.Date;

import com.iuni.data.persist.repository.config.HolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iuni.data.webapp.service.common.HolidayService;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService{
	
	private static final Logger logger = LoggerFactory.getLogger(HolidayService.class);
	@Autowired
	private HolidayRepository holidayRepository;

	@Override
	public boolean isHoliday(final Date date) {
		if(holidayRepository.isHoliday(date)==null){
			return false;
		}
		return true;
	}

	@Override
	public boolean isWeekDay(Date date) {
		// TODO Auto-generated method stub
		return false;
	}

}
