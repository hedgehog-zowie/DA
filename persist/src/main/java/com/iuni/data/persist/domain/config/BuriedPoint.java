package com.iuni.data.persist.domain.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * The persistent class for the IUNI_DA_BURIED_POINT database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_BURIED_POINT")
public class BuriedPoint extends AbstractConfig {

    @Column(name = "WEBSITE_CODE")
    private String websiteCode;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "PAGE_NAME")
    private String pageName;

    @Column(name = "PAGE_POSITION")
    private String pagePosition;

    @Column(name = "POINT_FLAG")
    private String pointFlag;

    @Column(name = "POINT_TYPE")
    private Integer pointType;

    @Override
    public String toString() {
        return "BuriedPoint{" +
                "websiteCode='" + websiteCode + '\'' +
                ", website='" + website + '\'' +
                ", pageName='" + pageName + '\'' +
                ", pagePosition='" + pagePosition + '\'' +
                ", pointFlag='" + pointFlag + '\'' +
                ", pointType=" + pointType +
                '}';
    }

    public String getWebsiteCode() {
        return websiteCode;
    }

    public void setWebsiteCode(String websiteCode) {
        this.websiteCode = websiteCode;
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

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("站点编码", "websiteCode");
        tableHeader.put("站点名称", "website");
        tableHeader.put("页面名称", "pageName");
        tableHeader.put("页面位置", "pagePosition");
        tableHeader.put("埋点编码", "pointFlag");
        tableHeader.put("添加时间", "createDate");
        tableHeader.put("备注", "desc");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<BuriedPoint> buriedPointList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (BuriedPoint buriedPoint : buriedPointList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("websiteCode", buriedPoint.getWebsiteCode());
            rowData.put("website", buriedPoint.getWebsite());
            rowData.put("pageName", buriedPoint.getPageName());
            rowData.put("pagePosition", buriedPoint.getPagePosition());
            rowData.put("pointFlag", buriedPoint.getPointFlag());
            rowData.put("createDate", String.valueOf(buriedPoint.getCreateDate()));
            rowData.put("desc", buriedPoint.getDesc());
            tableData.add(rowData);
        }
        return tableData;
    }

}
