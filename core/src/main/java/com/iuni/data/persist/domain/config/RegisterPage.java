package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the IUNI_DA_REGISTER_PAGE database table.
 * 
 */
@Entity
@Table(name="IUNI_DA_REGISTER_PAGE")
public class RegisterPage extends AbstractConfig {

	@Column(name="\"NAME\"")
	private String name;

    @Column(name="URL")
	private String url;

    public RegisterPage() {
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

}