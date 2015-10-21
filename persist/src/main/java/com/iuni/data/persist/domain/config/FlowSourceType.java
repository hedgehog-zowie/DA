package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * The persistent class for the IUNI_DA_FLOW_SOURCE_TYPE database table.
 */
@Entity
@Table(name = "IUNI_DA_FLOW_SOURCE_TYPE")
public class FlowSourceType extends AbstractConfig {

    @Column(name="\"NAME\"")
    private String name;

    //bi-directional many-to-one association to FlowSource
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "flowSourceType")
    private Set<FlowSource> flowSources;

    public FlowSourceType() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<FlowSource> getFlowSources() {
        return this.flowSources;
    }

    public void setFlowSources(Set<FlowSource> flowSources) {
        this.flowSources = flowSources;
    }

}