package com.jy.common.utils.auth;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.user.entity.User;

public class UserUtils {

	/**
	 *获取当前登录user 未登陆为null 
	 **/
	public static User getLoginUser() {
		User user = LoginInterceptor.getCurrentUser();
		return user;
	}
	
	/**
	 * 获取当前登陆user OrgId 
	 **/
	public static String getLoginUserOrgId() {
		User user = LoginInterceptor.getCurrentUser();
		if(null != user) {
			return user.getOrgId();
		}
		return null;
	}
}
