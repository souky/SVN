package com.jy.moudles.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.entity.MenuInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;

@Controller
public class MenuController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
	/**
	 * 获取菜单
	 * 
	 * @return ResultData
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getMenu() throws Exception{
		LOGGER.info("获取Menu Start");
		User user = LoginInterceptor.getCurrentUser();
		if(null == user) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}
		List<MenuInfo> list = userService.getMenusByUserId(user.getId());
		LOGGER.info("获取Menu End");
		return AsyncResponseData.getSuccess(list);
	}
	
}
