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
@Table(name = "IUNI_DA_WEBKPI_CHANNEL")
public class WebKpiByChannel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "\"TIME\"")
    private Date time;

    private String ttype;

    private long pv;

    private long uv;

    private long vv;

    private long ip;

    /**
     * 停留时长，单位ms
     */
    @Column(name = "STAY_TIME")
    private long stayTime;

    /**
     * 跳出次数
     */
    @Column(name = "TOTAL_JUMP")
    private long totalJump;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    public WebKpiByChannel() {
    }

    public WebKpiByChannel(Date time, String ttype, Channel channel, Date createDate) {
        this.time = time;
        this.ttype = ttype;
        this.channel = channel;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getVv() {
        return vv;
    }

    public void setVv(long vv) {
        this.vv = vv;
    }

    public long getIp() {
        return ip;
    }

    public void setIp(long ip) {
        this.ip = ip;
    }

    public long getStayTime() {
        return stayTime;
    }

    public void setStayTime(long stayTime) {
        this.stayTime = stayTime;
    }

    public long getTotalJump() {
        return totalJump;
    }

    public void setTotalJump(long totalJump) {
        this.totalJump = totalJump;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "WebKpiByChannel{" +
                "id=" + id +
                ", time=" + time +
                ", ttype='" + ttype + '\'' +
                ", pv=" + pv +
                ", uv=" + uv +
                ", vv=" + vv +
                ", ip=" + ip +
                ", stayTime=" + stayTime +
                ", totalJump=" + totalJump +
                ", createDate=" + createDate +
                ", channel=" + channel +
                '}';
    }

}