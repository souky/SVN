package com.jy.moudles.clockUser.entity;

import com.jy.common.entity.BaseEntity;
/**
*时钟用户表，该信息来源预制数据和考试管理平台同步，故主要信息保存在平台
*/
public class ClockUser extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 用户名称；该名称为显示名称
	 */
	private String userName;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户来源；0：预制；1：平台
	 */
	private int userResource;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUserResource() {
		return userResource;
	}

	public void setUserResource(int userResource) {
		this.userResource = userResource;
	}
	
}



