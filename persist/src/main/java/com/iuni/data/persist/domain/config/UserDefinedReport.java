package com.iuni.data.persist.domain.config;

import javax.persistence.*;

/**
 * 用户自定义报表定义
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_USER_DEFINED_REPORT")
public class UserDefinedReport extends AbstractConfig {

    @Column(name = "\"NAME\"")
    private String name;

    @Column(name = "\"PATH\"")
    private String path;

    @Column(name = "\"USER\"")
    private String user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
