package com.iuni.data.webapp.sso.dto;

import java.io.Serializable;
import java.util.List;

/**
 * SsoGetMenuResult
 * 
 * @version dp-admin-1.0.0
 */
public class SsoGetMenuResult implements Serializable {
	private static final long serialVersionUID = -4426142303758238919L;
	private String code;
	private List<Menu> menu;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
