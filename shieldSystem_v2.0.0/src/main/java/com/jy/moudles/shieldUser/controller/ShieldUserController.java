package com.jy.moudles.shieldUser.controller;

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
import com.jy.moudles.shieldUser.VO.ChangepswVO;
import com.jy.moudles.shieldUser.entity.ShieldUser;
import com.jy.moudles.shieldUser.service.ShieldUserService;

/** 
 * shieldUser实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shielduser")
public class ShieldUserController {
	
	@Autowired
	private ShieldUserService shielduserService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldUserController.class);
	
	/**
	 * 新增shielduser对象
	 * 
	 * @param shielduser
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldUser(ShieldUser shielduser) throws Exception{
		logger.info("新增ShieldUser Start");
		
		shielduserService.insertShieldUser(shielduser);
		
		logger.info("新增ShieldUser End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shielduser对象
	 * 
	 * @param shielduser
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldUser(ShieldUser shielduser) throws Exception{
		logger.info("修改ShieldUser Start");
		
		shielduserService.updateShieldUser(shielduser);
		
		logger.info("修改ShieldUser End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shielduser对象
	 * 
	 * @param shielduser
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldUser(ShieldUser shielduser) throws Exception{
		logger.info("删除ShieldUser Start");
		
		shielduserService.deleteShieldUserById(shielduser.getId());
		
		logger.info("删除ShieldUser End");
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
		ShieldUser shielduser = (ShieldUser)request.getSession().getAttribute(Global.USERSESSION);
		if(null == shielduser) {
			return AsyncResponseData.getLogicError("用户未登陆");
		}
		changepswVO.setShieldUser(shielduser);
		AsyncResponseData.ResultData resData = shielduserService.changePassword(changepswVO);
		logger.info("修改密码 end");
		return resData;
	}
	
	/**
	 * 获取shielduser对象
	 * 
	 * @param shielduser
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldUsers", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldUsers(ShieldUser shielduser) throws Exception{
		logger.info("获取ShieldUser Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ShieldUser> shieldusers= shielduserService.queryShieldUsersFilter(filter);
		logger.info("获取ShieldUser End");
		
		return AsyncResponseData.getSuccess(shieldusers);
	}
	
	/**
	 * 根据ID获取shielduser对象
	 * 
	 * @param shielduser
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldUserById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldUserById(String id) throws Exception{
		logger.info("获取ShieldUser Start");
		
		ShieldUser shielduser = new ShieldUser();
		
		shielduser = shielduserService.getShieldUserById(id);
		
		logger.info("获取ShieldUser End");
		
		return AsyncResponseData.getSuccess(shielduser);
	}
	
}
