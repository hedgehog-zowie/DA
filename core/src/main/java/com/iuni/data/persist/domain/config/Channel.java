package com.iuni.data.persist.domain.config;

import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.WebKpi;

import javax.persistence.*;
import java.util.Set;

/**
 * The persistent class for the IUNI_DA_CHANNEL database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_CHANNEL")
public class Channel extends AbstractConfig {

    private String code;

    @Column(name="\"NAME\"")
    private String name;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<PageWebKpi> pageWebKpis;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<ClickWebKpi> clickWebKpis;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<WebKpi> webKpis;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PageWebKpi> getPageWebKpis() {
        return pageWebKpis;
    }

    public void setPageWebKpis(Set<PageWebKpi> pageWebKpis) {
        this.pageWebKpis = pageWebKpis;
    }

    public Set<ClickWebKpi> getClickWebKpis() {
        return clickWebKpis;
    }

    public void setClickWebKpis(Set<ClickWebKpi> clickWebKpis) {
        this.clickWebKpis = clickWebKpis;
    }

    public Set<WebKpi> getWebKpis() {
        return webKpis;
    }

    public void setWebKpis(Set<WebKpi> webKpis) {
        this.webKpis = webKpis;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", cancelFlag=" + cancelFlag +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
