package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.config.Holiday;
import com.iuni.data.persist.domain.config.HolidayType;
import com.iuni.data.webapp.annotation.FormModel;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.response.StatusResponse;
import com.iuni.data.webapp.service.config.HolidayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
/**
 *  @author dan.wang@iuni.com
 *
 */
@Controller
@RequestMapping("/config/holiday")
public class HolidayConfigController {
	
	@Autowired 
	private HolidayConfigService holidayConfigService;
	
	/**
	 * 查询出所有节假日
	 */
	@RequestMapping
	public ModelAndView holidayConfig(@RequestParam(value = "pageNum", required = false) Integer currentPage,
										@RequestParam(value = "numPerPage", required = false) Integer pageSize){
		PageVO pageVO = PageVO.createPage(currentPage, pageSize);
		List<Holiday> list = holidayConfigService.findHolidays(pageVO);
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PageName.config_holiday.getPath());
		mv.addObject("holidays", list);
		mv.addObject("page", pageVO);
		return mv;
	}
	
	@RequestMapping("/add")
	public ModelAndView addButton(){
		ModelAndView mv = new ModelAndView();
		Holiday holiday = new Holiday();
		List<HolidayType> holidayTypes = holidayConfigService.findHolidayTypes();
		mv.addObject("holidayTypes",holidayTypes);
		mv.addObject("holiday", holiday);
		mv.setViewName(PageName.config_holiday_edit.getPath());
		return mv;
	}
	
	@RequestMapping("/edit")
	public ModelAndView  editHoliday(@RequestParam("id")long id){
		ModelAndView mv = new ModelAndView();
		Holiday holiday = holidayConfigService.editHoliday(id);
		List<HolidayType> holidayTypes = holidayConfigService.findHolidayTypes();
		mv.addObject("holidayTypes",holidayTypes);
		mv.addObject("holiday", holiday);
		mv.setViewName(PageName.config_holiday_edit.getPath());
		return mv;
	}
	
	/**
	 * 保存一条节假日。包括更新和新添加两种情况。
	 */
	@RequestMapping("/save")
	public @ResponseBody StatusResponse updateHoliday(@FormModel("holiday")Holiday holiday,
														@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
														@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate){
		
	
		StatusResponse dwzAjaxResult = null;
		
		holiday.setStartDate(startDate);
		holiday.setEndDate(endDate);
		
		boolean flag = false;
		if(holiday.getId()!=0){//更新
			flag = holidayConfigService.updateHoliday(holiday);
		}else{//添加
			flag = holidayConfigService.addHoliday(holiday);
		}
		//返回结果
		if(flag){
			dwzAjaxResult = new StatusResponse(StatusResponse.STATUS_CODE_OK,"保存成功",StatusResponse.CALL_BACK_TYPE_CLOSE,"config/holiday","","config/holiday");
		} else {
			dwzAjaxResult = new StatusResponse(StatusResponse.STATUS_CODE_ERROR,"保存失败" ,"","","","");
		}
		return dwzAjaxResult;
	}
	
	/**
	 * 删除一条节假日记录。设置cancelFlag为1
	 */
	@RequestMapping("/delete")
	public @ResponseBody StatusResponse deleteHoliday(@RequestParam("id")long id){
		StatusResponse dwzAjaxResult = null;
		if(holidayConfigService.deleteHoliday(id)){
			dwzAjaxResult = new StatusResponse(StatusResponse.STATUS_CODE_OK,"删除成功" ,"","","","");
		} else {
			dwzAjaxResult = new StatusResponse(StatusResponse.STATUS_CODE_ERROR,"删除失败" ,"","","","");
		}
		return dwzAjaxResult;
	}
}
