package com.iuni.data.persist.domain.webkpi;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.FlowSource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the IUNI_DA_WEBKPI database table.
 */
@Entity
@Table(name = "IUNI_DA_WEBKPI")
public class WebKpi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String area;

    private String city;

    private String country;

    private String county;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    private int ip;

    // 0 is not workday and 1 is workday
    private int workday;

    private String isp;

    @Column(name = "NEW_UV")
    private int newUv;

    private String province;

    private int pv;

    @Temporal(TemporalType.DATE)
    @Column(name = "\"TIME\"")
    private Date time;

    @Column(name = "TOTAL_JUMP")
    private int totalJump;

    @Column(name = "TOTAL_SIZE")
    private long totalSize;

    @Column(name = "TOTAL_TIME")
    private long totalTime;

    private String ttype;

    private int uv;

    private int vv;

    @Column(name = "STAY_TIME")
    private long stayTime;

    //bi-directional many-to-one association to FlowSource
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_ID")
    private FlowSource flowSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    public WebKpi() {
    }

    public WebKpi(String area, String city, String country, String county, Date createDate, String isp, String province, Date time, String ttype, FlowSource flowSource, Channel channel) {
        this.area = area;
        this.city = city;
        this.country = country;
        this.county = county;
        this.createDate = createDate;
        this.isp = isp;
        this.province = province;
        this.time = time;
        this.ttype = ttype;
        this.flowSource = flowSource;
        this.channel = channel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public int getWorkday() {
        return workday;
    }

    public void setWorkday(int workday) {
        this.workday = workday;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public int getNewUv() {
        return newUv;
    }

    public void setNewUv(int newUv) {
        this.newUv = newUv;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTotalJump() {
        return totalJump;
    }

    public void setTotalJump(int totalJump) {
        this.totalJump = totalJump;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getVv() {
        return vv;
    }

    public void setVv(int vv) {
        this.vv = vv;
    }

    public long getStayTime() {
        return stayTime;
    }

    public void setStayTime(long stayTime) {
        this.stayTime = stayTime;
    }

    public FlowSource getFlowSource() {
        return flowSource;
    }

    public void setFlowSource(FlowSource flowSource) {
        this.flowSource = flowSource;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Webkpi{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", county='" + county + '\'' +
                ", createDate=" + createDate +
                ", ip=" + ip +
                ", workday=" + workday +
                ", isp='" + isp + '\'' +
                ", newUv=" + newUv +
                ", province='" + province + '\'' +
                ", pv=" + pv +
                ", time=" + time +
                ", totalJump=" + totalJump +
                ", totalSize=" + totalSize +
                ", totalTime=" + totalTime +
                ", ttype='" + ttype + '\'' +
                ", uv=" + uv +
                ", vv=" + vv +
                ", stayTime=" + stayTime +
                ", flowSource=" + flowSource +
                ", channel=" + channel +
                '}';
    }
}