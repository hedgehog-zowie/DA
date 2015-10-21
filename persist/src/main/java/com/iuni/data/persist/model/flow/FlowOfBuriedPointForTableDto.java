package com.iuni.data.persist.model.flow;

import com.iuni.data.persist.domain.config.BuriedPoint;
import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class FlowOfBuriedPointForTableDto extends AbstractTableDto {

    private String day;
    private String website;
    private String pageName;
    private String pagePosition;
    private String pointFlag;
    private Long pv;
    private Long uv;
    private Long vv;
    private Long ip;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPagePosition() {
        return pagePosition;
    }

    public void setPagePosition(String pagePosition) {
        this.pagePosition = pagePosition;
    }

    public String getPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(String pointFlag) {
        this.pointFlag = pointFlag;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public Long getVv() {
        return vv;
    }

    public void setVv(Long vv) {
        this.vv = vv;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public void setBuriedPointInfo(BuriedPoint buriedPoint){
        setPointFlag(buriedPoint.getPointFlag());
        setWebsite(buriedPoint.getWebsite());
        setPageName(buriedPoint.getPageName());
        setPagePosition(buriedPoint.getPagePosition());
    }

    /**
     * 表头
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "day");
        tableHeader.put("站点名称", "website");
        tableHeader.put("页面名称", "pageName");
        tableHeader.put("页面位置", "pagePosition");
        tableHeader.put("埋点编码", "pointFlag");
        tableHeader.put("PV", "pv");
        tableHeader.put("UV", "uv");
        tableHeader.put("VV", "vv");
        tableHeader.put("IP", "ip");
        return tableHeader;
    }

    /**
     * 表数据
     * @param flowOfBuriedPointTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<FlowOfBuriedPointForTableDto> flowOfBuriedPointTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (FlowOfBuriedPointForTableDto flowOfBuriedPointTableDto : flowOfBuriedPointTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("day", flowOfBuriedPointTableDto.getDay());
            rowData.put("website", flowOfBuriedPointTableDto.getWebsite());
            rowData.put("pageName", flowOfBuriedPointTableDto.getPageName());
            rowData.put("pagePosition", flowOfBuriedPointTableDto.getPagePosition());
            rowData.put("pointFlag", flowOfBuriedPointTableDto.getPointFlag());
            rowData.put("pv", flowOfBuriedPointTableDto.getPv());
            rowData.put("uv", flowOfBuriedPointTableDto.getUv());
            rowData.put("vv", flowOfBuriedPointTableDto.getVv());
            rowData.put("ip", flowOfBuriedPointTableDto.getIp());
            tableData.add(rowData);
        }
        return tableData;
    }
}
