package com.iuni.data.persist.domain.config;

import javax.persistence.*;

/**
 * The persistent class for the IUNI_DA_BURIED_POINT_RELATION database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_BURIED_POINT_RELATION")
public class BuriedRelation extends AbstractConfig implements Comparable<BuriedRelation> {

    @ManyToOne
    @JoinColumn(name = "BURIED_POINT_ID")
    private BuriedPoint buriedPoint;

    @ManyToOne
    @JoinColumn(name = "BURIED_GROUP_ID")
    private BuriedGroup buriedGroup;

    @Column(name = "\"INDEX\"")
    private Integer index;

    public BuriedPoint getBuriedPoint() {
        return buriedPoint;
    }

    public void setBuriedPoint(BuriedPoint buriedPoint) {
        this.buriedPoint = buriedPoint;
    }

    public BuriedGroup getBuriedGroup() {
        return buriedGroup;
    }

    public void setBuriedGroup(BuriedGroup buriedGroup) {
        this.buriedGroup = buriedGroup;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public int compareTo(BuriedRelation o) {
        if (this.getIndex() == null || o.getIndex() == null)
            return 0;
        return this.getIndex().compareTo(o.getIndex());
    }
}
