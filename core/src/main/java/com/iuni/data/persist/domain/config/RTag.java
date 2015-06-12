package com.iuni.data.persist.domain.config;

import com.iuni.data.persist.domain.webkpi.ClickWebKpi;

import javax.persistence.*;
import java.util.Set;

/**
 * The persistent class for the IUNI_DA_RTAG database table.
 */
@Entity
@Table(name = "IUNI_DA_RTAG")
public class RTag extends AbstractConfig {

    private String info;

    @Column(name="\"NAME\"")
    private String name;

    private String parent;

    private String rtag;

    //bi-directional many-to-one association to RTagType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    private RTagType rTagType;

    @OneToMany(mappedBy = "rtag", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClickWebKpi> clickWebKpis;

    public RTag() {
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRtag() {
        return this.rtag;
    }

    public void setRtag(String rtag) {
        this.rtag = rtag;
    }

    public RTagType getrTagType() {
        return this.rTagType;
    }

    public void setrTagType(RTagType rTagType) {
        this.rTagType = rTagType;
    }

    public Set<ClickWebKpi> getClickWebKpis() {
        return clickWebKpis;
    }

    public void setClickWebKpis(Set<ClickWebKpi> clickWebKpis) {
        this.clickWebKpis = clickWebKpis;
    }

}