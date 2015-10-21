package com.iuni.data.persist.domain.config;

import javax.persistence.*;

/**
 * The persistent class for the IUNI_DA_AD database table.
 * 
 */
@Entity
@Table(name="IUNI_DA_AD")
public class Advertisement extends AbstractConfig{

	@Column(name="\"NAME\"")
	private String name;

	@Column(name="CHANNEL")
	private String channel;

	@Column(name="\"POSITION\"")
	private String position;

	@Column(name="\"TYPE\"")
	private String type;

	@Column(name="URL")
	private String url;

    public Advertisement() {
    }

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

    public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", cancelFlag=" + cancelFlag +
                ", channel='" + channel + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", url='" + url + '\'' +
                '}';
    }

}