package com.iuni.data.persist.domain.config;

import com.iuni.data.persist.domain.webkpi.WebKpi;

import javax.persistence.*;
import java.util.Set;

/**
 * The persistent class for the IUNI_DA_FLOW_SOURCE database table.
 */
@Entity
@Table(name = "IUNI_DA_FLOW_SOURCE")
public class FlowSource extends AbstractConfig {

    @Column(name="\"NAME\"")
    private String name;

    private String url;

    //bi-directional many-to-one association to FlowSourceType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    private FlowSourceType flowSourceType;

    //bi-directional many-to-one association to webkpi
    @OneToMany(mappedBy = "flowSource", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<WebKpi> webKpis;

    public FlowSource() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FlowSourceType getFlowSourceType() {
        return this.flowSourceType;
    }

    public void setFlowSourceType(FlowSourceType flowSourceType) {
        this.flowSourceType = flowSourceType;
    }

    public Set<WebKpi> getWebKpis() {
        return this.webKpis;
    }

    public void setWebKpis(Set<WebKpi> webKpis) {
        this.webKpis = webKpis;
    }
}