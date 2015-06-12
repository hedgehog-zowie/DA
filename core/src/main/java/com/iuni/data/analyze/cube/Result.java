package com.iuni.data.analyze.cube;

import com.iuni.data.common.TType;
import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.WebKpi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Result {

//    private Map<Date, Date> timeRangeMap;
    private Date time;
    private TType tType;
    private final Map<String, PageWebKpi> pageWebKpiMap;
    private final Map<String, ClickWebKpi> clickWebKpiMap;
    private final Map<String, WebKpi> pageWebKpiForWholeSiteMap;

    public Result(){
        clickWebKpiMap = new HashMap<>();
        pageWebKpiMap = new HashMap<>();
        pageWebKpiForWholeSiteMap = new HashMap<>();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public TType gettType() {
        return tType;
    }

    public void settType(TType tType) {
        this.tType = tType;
    }

    public Map<String, PageWebKpi> getPageWebKpiMap() {
        return pageWebKpiMap;
    }

    public Map<String, ClickWebKpi> getClickWebKpiMap() {
        return clickWebKpiMap;
    }

    public Map<String, WebKpi> getPageWebKpiForWholeSiteMap() {
        return pageWebKpiForWholeSiteMap;
    }

    public void clear(){
        this.clickWebKpiMap.clear();
        this.pageWebKpiMap.clear();
        this.pageWebKpiForWholeSiteMap.clear();
    }

    public void addPageWebKpi(Map<String, PageWebKpi> pageWebKpiMap){
        this.pageWebKpiMap.putAll(pageWebKpiMap);
    }

    public void addClickPageWebKpi(Map<String, ClickWebKpi> clickWebKpiMap){
        this.clickWebKpiMap.putAll(clickWebKpiMap);
    }

    public void addPageWebKpiForWholeSiteMap(Map<String, WebKpi> pageWebKpiForWholeSiteMap){
        this.pageWebKpiForWholeSiteMap.putAll(pageWebKpiForWholeSiteMap);
    }

}
