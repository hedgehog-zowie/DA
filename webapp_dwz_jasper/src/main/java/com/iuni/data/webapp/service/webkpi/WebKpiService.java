package com.iuni.data.webapp.service.webkpi;

import com.iuni.data.persist.domain.webkpi.WebKpi;

import java.util.Date;
import java.util.List;

public interface WebKpiService {

	/*
     * 流量-总体分析
     * chaolumen
     */
	public List<WebKpi> getWebKpi(Date startDate, Date endDate);

	/**
	 * 流量 - 区域分析
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<WebKpi> getWebKpiGroupByArea(Date startDate, Date endDate);

	/**
	 * 流量 - 来源分析
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<WebKpi> getWebKpiGroupBySource(Date startDate, Date endDate);
}
