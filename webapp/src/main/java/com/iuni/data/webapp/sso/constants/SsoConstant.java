/*
 * @(#)SSOConstant.java 2013-8-26
 *
 * Copyright 2013 Shenzhen Gionee,Inc. All rights reserved.
 */
package com.iuni.data.webapp.sso.constants;

import com.iuni.data.webapp.sso.utils.PropertiesUtil;

/**
 * @ClsssName SsoConstant
 * @author ZuoChangjun 2013-8-26
 * @version dp-admin-1.0.0
 */
public class SsoConstant {
	/**
	 * 应用名
	 */
	public static final String APP_NAME="ias";
	
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * 与UUC对接的常量
	 */
	public static final String UUC_URL = PropertiesUtil.get("da.uuc.url");//用户中心地址
	public static final String UUC_AUTHC_TICKET_URI = "/sso?";//取用户ticket
	public static final String UUC_AUTHC_LOGOUT_URI = "/logout?";//登出
	public static final String UUC_AUTHC_CALLBACK_URI = "/authcCallback";//认证回调接口，如   http://18.8.0.28:8080/ias/authcCallback?ticket=ST-271-5m6bog4ayUMUABXIHJU7-sso
	public static final String UUC_AUTHC_VALIDATE_URI = "/cas/validate";//验证ticket
	public static final String UUC_AUTHZ_CHECK_URI = "/cas/validlogin";//检测是否登录
	public static final String UUC_FETCH_MENU_URI = "/cas/menu";//取用户菜单
	public static final String UUC_FETCH_PERMISSION_URI = "/cas/permission";//取用户权限
//	public static final String UUC_AUTHC_VALIDATE_URI = "/api/queryStock.action";//验证ticket
//	public static final String UUC_AUTHZ_CHECK_URI = "/api/queryStock.action";//检测是否登录
//	public static final String UUC_FETCH_MENU_URI = "/api/queryStock.action";//取用户菜单
//	public static final String UUC_FETCH_PERMISSION_URI = "/api/queryStock.action";//取用户权限
	public static final String UUC_APP_DA_ID = PropertiesUtil.get("da.uuc.app.id");//DA在用户中心配置的应用ID
	public static final String UUC_APP_DA_KEY = PropertiesUtil.get("da.uuc.app.key");//DA在用户中心配置的应用ID
}
