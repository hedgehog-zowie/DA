package com.iuni.data.ws.dto;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class UV extends PageResult {
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
    private long uv;

    public UV(String url, String adId, long uv) {
        this.url = url;
        this.adId = adId;
        this.uv = uv;
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

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    @Override
    public String toString() {
        return "UV{" +
                "url='" + url + '\'' +
                ", adId='" + adId + '\'' +
                ", uv=" + uv +
                '}';
    }

    @Override
    public int compareTo(PageResult o) {
        return (int) (((UV) o).getUv() - this.getUv());
    }
}
