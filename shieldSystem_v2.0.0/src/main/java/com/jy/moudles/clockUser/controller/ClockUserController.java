package com.jy.moudles.clockUser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.config.Global;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.annotation.logAspect.JRYLogging;
import com.jy.common.persistence.annotation.logAspect.OpType;
import com.jy.moudles.clockLog.utils.LogUtils;
import com.jy.moudles.clockUser.VO.ChangepswVO;
import com.jy.moudles.clockUser.entity.ClockUser;
import com.jy.moudles.clockUser.service.ClockUserService;

/** 
 * ClockUser实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clockuser")
public class ClockUserController {
	
	@Autowired
	private ClockUserService clockuserService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockUserController.class);
	
	/**
	 * 新增clockuser对象
	 * 
	 * @param clockuser
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockUser(ClockUser clockuser) throws Exception{
		logger.info("新增ClockUser Start");
		
		clockuserService.insertClockUser(clockuser);
		
		logger.info("新增ClockUser End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clockuser对象
	 * 
	 * @param clockuser
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateClockUser(ClockUser clockuser) throws Exception{
		logger.info("修改ClockUser Start");
		
		clockuserService.updateClockUser(clockuser);
		
		logger.info("修改ClockUser End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clockuser密码
	 * 
	 * @param clockuser
	 * @throws Exception
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.EDIT, opInfo = "修改密码。", opReplaceParams = {""})
	public AsyncResponseData.ResultData changePassword(ChangepswVO changepswVO,HttpServletRequest request) throws Exception{
		logger.info("修改密码 Start");
		ClockUser clockUser = (ClockUser)request.getSession().getAttribute(Global.USERSESSION);
		if(null == clockUser) {
			return AsyncResponseData.getLogicError("用户未登陆");
		}
		changepswVO.setClockUser(clockUser);
		AsyncResponseData.ResultData resData = clockuserService.changePassword(changepswVO);
		LogUtils.addLog("用户模块", "修改密码 id:"+clockUser.getId(), LogUtils.UPDATE, request);
		logger.info("修改密码 end");
		return resData;
	}
	
	/**
	 * 删除clockuser对象
	 * 
	 * @param clockuser
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClockUser(ClockUser clockuser) throws Exception{
		logger.info("删除ClockUser Start");
		
		clockuserService.deleteClockUserById(clockuser.getId());
		
		logger.info("删除ClockUser End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clockuser对象
	 * 
	 * @param clockuser
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockUsers", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockUsers(ClockUser clockuser) throws Exception{
		logger.info("获取ClockUser Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ClockUser> clockusers= clockuserService.queryClockUsersFilter(filter);
		logger.info("获取ClockUser End");
		
		return AsyncResponseData.getSuccess(clockusers);
	}
	
	/**
	 * 根据ID获取clockuser对象
	 * 
	 * @param clockuser
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockUserById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockUserById(String id) throws Exception{
		logger.info("获取ClockUser Start");
		
		ClockUser clockuser = new ClockUser();
		
		clockuser = clockuserService.getClockUserById(id);
		
		logger.info("获取ClockUser End");
		
		return AsyncResponseData.getSuccess(clockuser);
	}
	
}
