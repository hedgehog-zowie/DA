package com.iuni.data.persist.domain.webkpi;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.RTag;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the IUNI_DA_WEBKPI_CLICK database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_WEBKPI_CLICK")
public class ClickWebKpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "\"TIME\"")
    private Date time;

    private String ttype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RTAG_ID")
    private RTag rtag;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    public ClickWebKpi() {
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

    public RTag getRtag() {
        return rtag;
    }

    public void setRtag(RTag rtag) {
        this.rtag = rtag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ClickWebKpi{" +
                "id=" + id +
                ", time=" + time +
                ", ttype='" + ttype + '\'' +
                ", rtag=" + rtag +
                ", count=" + count +
                ", channel=" + channel +
                ", createDate=" + createDate +
                '}';
    }
}
