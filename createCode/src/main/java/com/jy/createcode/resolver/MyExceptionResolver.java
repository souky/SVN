package com.jy.createcode.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jy.createcode.utils.Logger;

public class MyExceptionResolver implements HandlerExceptionResolver {
	private Logger logger = Logger.getLogger(this.getClass());

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("==============异常开始=============");
		ex.printStackTrace();
		logger.error("==============异常结束=============");
		ModelAndView mv = new ModelAndView("error");//进入error页面
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}

}
