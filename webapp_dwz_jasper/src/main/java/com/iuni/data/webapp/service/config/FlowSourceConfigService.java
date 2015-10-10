package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.FlowSourceType;
import com.iuni.data.webapp.common.PageVO;

import java.util.List;

/**
 *  @author dan.wang@iuni.com
 *
 */
public interface FlowSourceConfigService {
	 /**
	 * 查询所有流量来源，即传入的对象FlowSource为空
	 * 根据条件搜索流量来源，传入对象FlowSource不为空
	  */
	public List<FlowSource> listFlowSources(final FlowSource flowSource,PageVO pageVO);
	/**
	 *   编辑一条流量来源
	 */
	public FlowSource editFlowSource(Long id);
	/**
	 *  跟新一条流量来源
	 */
	public boolean updateFlowSource(FlowSource flowSource);
	 /**
	  *  新增一条流量来源
	  */
	public boolean addFlowSource(FlowSource flowSource);
	 /**
	  * 查询所有的流量来源类型
	  */
	public List<FlowSourceType> findFlowSourceTypes();
	

}
