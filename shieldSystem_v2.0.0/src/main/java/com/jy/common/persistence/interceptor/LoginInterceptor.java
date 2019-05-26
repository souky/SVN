package com.jy.common.persistence.interceptor;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jy.common.config.Global;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.mapper.JsonMapper;
import com.jy.moudles.clockUser.entity.ClockUser;

@SuppressWarnings("unused")
public class LoginInterceptor implements HandlerInterceptor {
	
	/**
	 * 忽略url
	 */
	private final static String[] IGNORE_URI = {"/loginOut","/loginIn","/","/index.html"};
	
	private static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();



	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		boolean flag = false;
		boolean urlFlag = false;
		String url = request.getRequestURL().toString();
		
		// 跨域拦截
        String ref = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", ref);
		
		//过滤不需要登陆的地址
		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				flag = true;
				urlFlag = true;
				break;
			}
		}
		
		//验证登陆状态
		if (!urlFlag) {
			HttpSession session = request.getSession();
			if(null != session){
				ClockUser clockUser = (ClockUser) session.getAttribute(Global.USERSESSION);
				if(null != clockUser) {
					flag = true;
					urlFlag = true;
				}
			}
		}
		
		if (!flag || !urlFlag) {
			//登陆失败错误信息回复
			response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JsonMapper.toJsonString(AsyncResponseData.getDenied("用户未登陆")));
            response.getWriter().close();
		}
		return (flag && urlFlag);
	}
    
	
}
