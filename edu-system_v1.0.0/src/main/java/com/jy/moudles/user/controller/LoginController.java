package com.jy.moudles.user.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jy.common.persistence.interceptor.LoginInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.MD5Util;
import com.jy.common.utils.VerifyCodeUtil;
import com.jy.common.utils.auth.AuthBuilder;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;

@Controller
@RequestMapping(value="/")
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData login(HttpServletRequest request, HttpServletResponse response, @RequestParam String userName, @RequestParam String psw) {
		LOGGER.info("登录验证");

		User user = userService.getUserByNameAndPwd(userName, MD5Util.MD5(psw));

		String strBackUrl = request.getQueryString();

        String secretKey = "";

        if (StringUtils.isBlank(strBackUrl)){
            return AsyncResponseData.getDenied("该用户无权限登陆");
        }

        if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_MANAGER)) {
            secretKey = LoginInterceptor.SECRETKEY_JY_EDU_EVALUATION_MANAGER;
        } else if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_VIEWER)) {
            secretKey = LoginInterceptor.SECRETKEY_JY_EDU_EVALUATION_VIEWER;
        } else if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_MB_VIEWER)) {
            secretKey = LoginInterceptor.SECRETKEY_JY_EDU_EVALUATION_MB_VIEWER;
        } else {
            return AsyncResponseData.getSuccess().asParamError("用户名或密码错误");
        }

        if (null != user){
//			if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_MANAGER) && "3".equals(user.getRoleId())){
//				return AsyncResponseData.getDenied("该用户无权限登陆");
//			} else {
//				AuthBuilder.setCookie(response, secretKey, user.getId(), user.getUserName());
//				return AsyncResponseData.getSuccess(user);
//			}
            AuthBuilder.setCookie(response, secretKey, user.getId(), user.getUserName());
            return AsyncResponseData.getSuccess(user);
		} else {
			return AsyncResponseData.getSuccess().asParamError("用户名或密码错误");
		}
	}

	@RequestMapping(value = "/logout" , method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData logout(HttpServletRequest request,HttpServletResponse response) {
		LOGGER.info("登录验证");
        String strBackUrl = request.getQueryString();

        if (StringUtils.isBlank(strBackUrl)){
            return AsyncResponseData.getDenied("该用户无权限登陆");
        }

        String secretKey = "";
        if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_MANAGER)) {
            secretKey = LoginInterceptor.SECRETKEY_JY_EDU_EVALUATION_MANAGER;
        } else if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_VIEWER)) {
            secretKey = LoginInterceptor.SECRETKEY_JY_EDU_EVALUATION_VIEWER;
        } else if (strBackUrl.contains(LoginInterceptor.JY_EDU_EVALUATION_MB_VIEWER)) {
            secretKey = LoginInterceptor.SECRETKEY_JY_EDU_EVALUATION_MB_VIEWER;
        }

		AuthBuilder.clearCookie(response, secretKey);
		return AsyncResponseData.getSuccess();
	}


	@RequestMapping(value = "/removeCookie", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData removeCookie(HttpServletResponse response) {
		Cookie nameCookie = new Cookie("username", null);
		nameCookie.setMaxAge(0);
		Cookie pwdCookie = new Cookie("password", null);
		pwdCookie.setMaxAge(0);

		response.addCookie(nameCookie);
		response.addCookie(pwdCookie);

		return AsyncResponseData.getSuccess();
	}

	@RequestMapping(value = "/create_captcha")
	public void genCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
		HttpSession session = request.getSession(true);
		session.setAttribute("captcha", verifyCode.toLowerCase());
		int w = 235, h = 35;
		VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
}
