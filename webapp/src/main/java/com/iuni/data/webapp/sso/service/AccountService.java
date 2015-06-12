package com.iuni.data.webapp.sso.service;

import com.iuni.data.webapp.sso.dto.Menu;
import com.iuni.data.webapp.sso.dto.ShiroUser;
import com.iuni.data.webapp.sso.dto.User;

import java.util.List;

public interface AccountService {
	/**
	 * 查找用户.
	 */
	User findUserByLoginName(String loginName);

	/**
	 * 取当前用户.
	 */
	ShiroUser getCurrentUser();

	/**
	 * 取用户菜单树.
	 */

	Menu getMenusByUserId(Long userId);

	/**
	 * 取权限列表.
	 */
	List<String> getPermissionList(Long userId);
	
	/**
	 * 取权限列表
	 */
	List<Menu> getPermissions(Long userId);

	/**
	 * 判断是否已经过认证.
	 */
	boolean isAuthenticated();

	/**
	 * 登录认证
	 */
	void authenticate(String loginName, String password);
	
	/**
	 * 判断用户是否具有权限
	 * @param permission 权限，如"增加分类"
	 * @return
	 */
	boolean isPermitted(String permission);

}
