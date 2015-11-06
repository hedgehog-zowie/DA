package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.util.Set;

/**
 * The persistent class for the IUNI_DA_CHANNEL database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_CHANNEL_TYPE")
public class ChannelType extends AbstractConfig {

    @Column(name = "\"NAME\"")
    private String name;

    @Column(name = "CODE")
    private String code;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "channelType")
    private Set<Channel> channelSet;

    @Override
    public String toString() {
        return "ChannelType{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Channel> getChannelSet() {
        return channelSet;
    }

    public void setChannelSet(Set<Channel> channelSet) {
        this.channelSet = channelSet;
    }
}
