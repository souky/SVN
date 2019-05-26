package com.jy.moudles.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.config.Global;
import com.jy.common.ehcache.EhcacheUtil;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.MD5Util;
import com.jy.moudles.clockUser.entity.ClockUser;
import com.jy.moudles.clockUser.service.ClockUserService;

import net.sf.ehcache.Element;

@Controller
public class LoginController {
	
	@Autowired
	private ClockUserService clockUserService;
	
	// 登陆方法
	@RequestMapping(value = "/loginIn",method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData loginIn(String loginName,String password,HttpServletRequest request) {
		
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginName) ) {
			return AsyncResponseData.getSuccess().asParamError("登录名或密码为空");
		}
		password = MD5Util.MD5(password);
		ClockUser clockUser = clockUserService.getUserByPassword(loginName, password);
		if(null == clockUser) {
			return AsyncResponseData.getSuccess().asParamError("用户名或密码错误");
		}else {
			// 登陆成功  放入session信息
//			EhcacheUtil util = EhcacheUtil.getInstance();
//			Element e = new Element("currentUser", clockUser);
//			util.getCache("userCache").put(e);
			request.getSession().setAttribute(Global.USERSESSION, clockUser);
		}
		
		return AsyncResponseData.getSuccess();
	}
	
	
	// 登出方法
	@RequestMapping(value = "/loginOut",method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData loginOut(HttpServletRequest request) {
		
		request.getSession().removeAttribute(Global.USERSESSION);
		
		return AsyncResponseData.getSuccess();
	}
	
	// 获取登陆用户
	@RequestMapping(value = "/getLoginUesr",method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getLoginUesr(HttpServletRequest request) {
		
		ClockUser clockUser = (ClockUser) request.getSession().getAttribute(Global.USERSESSION);
		if(null == clockUser) {
			return AsyncResponseData.getSuccess().asLogicError();
		}
		return AsyncResponseData.getSuccess(clockUser);
	}
	
	
}
