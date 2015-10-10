package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.common.ConfigConstants;
import com.iuni.data.persist.domain.config.Holiday;
import com.iuni.data.persist.domain.config.HolidayType;
import com.iuni.data.persist.repository.config.HolidayRepository;
import com.iuni.data.persist.repository.config.HolidayTypeRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.HolidayConfigService;
import com.iuni.data.webapp.sso.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("holidayConfigService")
public class HolidayConfigServiceImpl implements HolidayConfigService {

	private static final Logger logger = LoggerFactory.getLogger(HolidayConfigService.class);
	@Autowired
	private HolidayRepository holidayRepository;
	
	@Autowired
	private HolidayTypeRepository holidayTypeRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Transactional(readOnly=true)
	@Override
	public List<Holiday> findHolidays(PageVO pageVO) {
		List<Holiday> holidayList = new ArrayList<Holiday>();
		
		Sort sort = new Sort(Direction.ASC,"startDate");
		Pageable pageable = new PageRequest(pageVO.getCurrentPage()-1 ,pageVO.getPageSize(),sort);
		
		Page<Holiday> holidayPage = holidayRepository.findHolidays2(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL,getCurrentYear(),pageable);
		holidayList = holidayPage.getContent();
		pageVO.setTotalPage(holidayPage.getTotalPages());
		pageVO.setTotalRecord(holidayPage.getTotalElements());
		return holidayList;
	}
	
	@Transactional
	@Override
	public boolean deleteHoliday(Long id) {
		try{
			if (holidayRepository.exists(id)) {
				Holiday holiday = holidayRepository.findOne(id);
				holiday.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_CANCEL);
				holiday.setUpdateBy(accountService.getCurrentUser().getLoginName());
				holiday.setUpdateDate(new Date());
				holidayRepository.save(holiday);
			}
		}catch (Exception e ){
			 logger.error(e.getLocalizedMessage());
	         return false;
		}
		return true;
	}
	
	@Transactional
	@Override
	public boolean addHoliday(Holiday holiday) {
		try {
			holiday.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
			holiday.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
			holiday.setYr(getCurrentYear());
			holiday.setCreateBy(accountService.getCurrentUser().getLoginName());
			holiday.setCreateDate(new Date());
			holiday.setUpdateBy(accountService.getCurrentUser().getLoginName());
			holiday.setUpdateDate(new Date());
			holidayRepository.save(holiday);
			return true;
		} catch (Exception e ){
			 logger.error(e.getLocalizedMessage());
	         return false;
		}
	}

	@Override
	public Holiday editHoliday(Long id) {
		return holidayRepository.findOne(id);
	}
	
	@Transactional
	@Override
	public boolean updateHoliday(Holiday holiday) {
		try {
			Holiday hld = holidayRepository.findOne(holiday.getId());
			hld.setName(holiday.getName());
			hld.setStartDate(holiday.getStartDate());
			hld.setEndDate(holiday.getEndDate());
			
			HolidayType holidayType = holidayTypeRepository.findOne(holiday.getHolidayType().getId());
			hld.setHolidayType(holidayType);
			
			hld.setUpdateBy(accountService.getCurrentUser().getLoginName());
			hld.setUpdateDate(new Date());
			holidayRepository.save(hld);
			return true;
		} catch (Exception e ){
			 logger.error(e.getLocalizedMessage());
	         return false;
		}
	}
	
	@Override
	public List<HolidayType> findHolidayTypes() {
		return holidayTypeRepository.findAll();
	}
	
	/**
	 * @return 当年年份
	 */
	private  int getCurrentYear(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

}
