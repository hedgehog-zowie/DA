package com.iuni.data.webapp.service.config;

import java.util.List;

import com.iuni.data.persist.domain.config.Holiday;
import com.iuni.data.persist.domain.config.HolidayType;
import com.iuni.data.webapp.common.PageVO;

/**
 *  @author dan.wang@iuni.com
 *
 */
public interface HolidayConfigService {
	 /**
	  *  查找所有假日,cancelFlag 为0为有效记录
	  */
	public List<Holiday> findHolidays(PageVO pageVO);
	 /**
	  *  删除一条假日
	  */
	public boolean deleteHoliday(Long id);
	/**
	 *  添加一条假日信息
	 */
	public boolean addHoliday(Holiday holiday);
	/**
	 *  按照id查询一条假日
	 */
	public Holiday editHoliday(Long id);
	/**
	 * 更新一条假日信息
	 */
	public boolean updateHoliday(Holiday holiday);
	/**
	 * 查询所有节假日类型
	 */
	public List<HolidayType> findHolidayTypes();
	
}
