package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.FlowSourceType;
import com.iuni.data.webapp.annotation.FormModel;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.response.StatusResponse;
import com.iuni.data.webapp.service.config.FlowSourceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
/**
 *  @author dan.wang@iuni.com
 *
 */
@Controller
@RequestMapping("/config/flowSource")
public class FlowSourceConfigController {
	
	@Autowired
	private FlowSourceConfigService flowSourceConfigService;
	
	/**
	 * 查询所有流量来源，即传入的对象FlowSource为空
	 * 根据条件搜索流量来源，传入对象FlowSource不为空。
	 * @return
	 */
	@RequestMapping(method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView flowSource(@RequestParam(value = "pageNum", required = false) Integer currentPage,
            						@RequestParam(value = "numPerPage", required = false) Integer pageSize,
										@FormModel("flowSource")FlowSource flowSource){
		PageVO pageVO = PageVO.createPage(currentPage, pageSize);
		List<FlowSource> flowSources = flowSourceConfigService.listFlowSources(flowSource,pageVO);
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PageName.config_flowSource.getPath());
		mv.addObject("flowSources", flowSources);
		mv.addObject("page", pageVO);
		return mv;
	}
	
	@RequestMapping("/add")
	public ModelAndView addButton(){
		List<FlowSourceType> flowSourceTypes = flowSourceConfigService.findFlowSourceTypes();
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PageName.config_flowSource_edit.getPath());
		FlowSource flowSource = new FlowSource();
		mv.addObject("flowSource", flowSource);
		mv.addObject("flowSourceTypes", flowSourceTypes);
		return mv;
	}
	
	
	@RequestMapping("/edit")
	public ModelAndView editFlowSource(@RequestParam("id") long id){
		FlowSource flowSource = flowSourceConfigService.editFlowSource(id);
		List<FlowSourceType> flowSourceTypes = flowSourceConfigService.findFlowSourceTypes();
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PageName.config_flowSource_edit.getPath());
		mv.addObject("flowSourceTypes", flowSourceTypes);
		mv.addObject("flowSource", flowSource);
		return mv;
	} 
	
	/**
	 * 保存一条流量来源，包括更新和新增加一条。
	 */
	@RequestMapping("/save")
	public @ResponseBody StatusResponse updateFlowSource(@FormModel("flowSource")FlowSource flowSource){
		StatusResponse dwzAjaxResult = null;
		boolean flag = false;
		if(flowSource.getId()!=0){//更新
			flag = flowSourceConfigService.updateFlowSource(flowSource);
		} else if(flowSource.getId()==0){//添加
			flag = flowSourceConfigService.addFlowSource(flowSource);
		}
		//
		if(flag){
			dwzAjaxResult = new StatusResponse(StatusResponse.STATUS_CODE_OK,"保存成功" ,StatusResponse.CALL_BACK_TYPE_CLOSE,"config/flowSource","","config/flowSource");
		} else {
			dwzAjaxResult = new StatusResponse(StatusResponse.STATUS_CODE_ERROR,"保存失败" ,"","","","");
		}
		return dwzAjaxResult;
	}
	
	
}
