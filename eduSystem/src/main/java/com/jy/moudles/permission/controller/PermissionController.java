package com.jy.moudles.permission.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.permission.entity.Permission;
import com.jy.moudles.permission.service.PermissionService;
import com.jy.moudles.user.entity.User;

/** 
 * 权限实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
	

	/**
	 * 获取权限对象
	 * 
	 * @return ResultData
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPermission", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryPermission(String roleId) throws Exception{
		LOGGER.info("获取Permission Start");
		
		User currentUser = LoginInterceptor.getCurrentUser();
		
		List<Permission> permissions= permissionService.getPermissionsByUser(currentUser, roleId);

		return AsyncResponseData.getSuccess(permissions);
	}
	
	
	/**
	 * 获取权限对象
	 * 
	 * @return ResultData
	 * @throws Exception
	 */
	@RequestMapping(value = "/grantPermissions", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData grantPermissions(@RequestParam(required = false) List<String> permissionIds, String roleId) throws Exception{
		LOGGER.info("授权开始");
		
		User currentUser = LoginInterceptor.getCurrentUser();
		
		permissionService.grantPermissions(permissionIds, roleId, currentUser);
		
		return AsyncResponseData.getSuccess();
	}
	
	
	
}
