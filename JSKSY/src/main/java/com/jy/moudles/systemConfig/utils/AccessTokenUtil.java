package com.jy.moudles.systemConfig.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jy.moudles.systemConfig.constant.SystemConstant;
import com.jy.moudles.systemConfig.service.SystemConfigService;

public class AccessTokenUtil {

	private static WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
	private static SystemConfigService systemconfigService = ctx.getBean(SystemConfigService.class);
	
	
	public static String getAccessToken() {
		return systemconfigService.getSystemConfigByKey(SystemConstant.ACCESSTOKEN);
	}
	
	public static String getAppId() {
		return systemconfigService.getSystemConfigByKey(SystemConstant.APPID);
	}
	
	public static String getAppKey() {
		return systemconfigService.getSystemConfigByKey(SystemConstant.APPKEY);
	}
	
	public static String getOrgCode() {
		return systemconfigService.getSystemConfigByKey(SystemConstant.ORGCODE);
	}
	
	public static String getOrgIdenCode() {
		return systemconfigService.getSystemConfigByKey(SystemConstant.ORGIDENCODE);
	}
}
