package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the IUNI_DA_RTAG_TYPE database table.
 */
@Entity
@Table(name = "IUNI_DA_RTAG_TYPE")
public class RTagType extends AbstractConfig {

    @Column(name = "\"NAME\"")
    private String name;

    //bi-directional many-to-one association to RTag
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "rTagType")
    private List<RTag> rTags;

    public RTagType() {
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

    public List<RTag> getrTags() {
        return rTags;
    }

    public void setrTags(List<RTag> rTags) {
        this.rTags = rTags;
    }
}