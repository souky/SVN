package com.jy.moudles.clockLog.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jy.common.config.Global;
import com.jy.moudles.clockLog.entity.ClockLog;
import com.jy.moudles.clockLog.service.ClockLogService;
import com.jy.moudles.clockUser.entity.ClockUser;

public class LogUtils {

	public static void addLog(String moulde,String msg,int type,HttpServletRequest request) {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		ClockLogService clockLogService = context.getBean(ClockLogService.class);
		//拿到用户
		ClockUser clockUser = (ClockUser) request.getSession().getAttribute(Global.USERSESSION);
		if(null != clockUser) {
			ClockLog clockLog = new ClockLog();
			clockLog.setOperationMoudle(moulde);
			clockLog.setOperationType(type);
			clockLog.setOperationInfo(msg);
			clockLog.setOperationTime(new Date());
			clockLog.setOperationUser(clockUser.getId());
			
			clockLogService.insertClockLog(clockLog);
		}
	}
	
	public static int ADD = 1;
	public static int UPDATE = 2;
	public static int DELETE = 3;
}
