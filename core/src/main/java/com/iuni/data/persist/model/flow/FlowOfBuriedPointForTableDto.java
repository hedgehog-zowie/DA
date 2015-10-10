package com.iuni.data.persist.model.flow;

import com.iuni.data.persist.domain.config.BuriedPoint;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class FlowOfBuriedPointForTableDto {

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
}
