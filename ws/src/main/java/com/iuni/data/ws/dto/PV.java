package com.iuni.data.ws.dto;

/**
 * 浏览量数据返回
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PV extends PageResult{
    /**
     * URL
     */
    private String url;
    /**
     * 渠道编号
     */
    private String adId;
    /**
     * 数量
     */
    private long pv;

    public PV(String url, String adId, long pv){
        this.url = url;
        this.adId = adId;
        this.pv = pv;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    @Override
    public String toString() {
        return "PV{" +
                "url='" + url + '\'' +
                ", adId='" + adId + '\'' +
                ", pv=" + pv +
                '}';
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((PV) o).getPv() - this.getPv());
    }
}
