package com.jy.moudles.buttInterface.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.buttInterface.service.ButtInterfaceService;
import com.jy.moudles.buttInterface.utils.Constant;
import com.jy.moudles.buttInterface.utils.DataUtils;
import com.jy.moudles.buttInterface.utils.SendPostUtils;
import com.jy.moudles.buttInterface.utils.VerificationUtils;
import com.jy.moudles.user.entity.User;

/**
 * 跳转接口
 * @author Administrator
 *
 */
@RestController
@RequestMapping("skip")
public class SkipController {
	
	Logger logger = LoggerFactory.getLogger(SkipController.class);
	
	@Autowired
	private ButtInterfaceController buf;
	
	
	/**
	 * 同步信息
	 * @param examId 考试id
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年2月5日 上午9:30:23
	 */
	@RequestMapping(value = "/synchronizationMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData synchronizationMsg(String examId) {
		try {
			
			User user = LoginInterceptor.getCurrentUser();
			if (DataUtils.isNull(user)) {
				return AsyncResponseData.getSuccess().asParamError("请重新登录");
			}
			
			if (DataUtils.isNull(examId)) {
				return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
			}
			
			StringBuffer param = new StringBuffer();
			param.append("examId="+examId);
			//all 同步所有信息  exam同步考试信息    student同步考生信息     teacher同步教师信息
			param.append("&type=all");
			String msg = SendPostUtils.webVisit(Constant.URL+"/syncExamItemInfo", SendPostUtils.CHARSET, param.toString());
			
			if (DataUtils.isNull(msg)) {
				return AsyncResponseData.getSuccess().asParamError("同步信息失败");
			}
			
			Map<String,Object> objMap = (Map<String,Object>)JSONObject.parseObject(msg);
			
			if ("10000".equals(objMap.get("errCode").toString())) {
				//修改考试信息的同步状态
//				Map<String,Object> pm = new HashMap<>();
//				pm.put("synchronousState", 1);
//				pm.put("examId", examId);
//				buttInterfaceService.updateExamInfo(pm);
				
				return AsyncResponseData.getSuccess("同步信息成功");
			}
			
			logger.info("------------------msg:"+msg);
			return AsyncResponseData.getSuccess().asParamError(objMap.get("exception").toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			return AsyncResponseData.getSuccess().asParamError("同步信息失败");
		}
		
	}
	
	/**
	 * 获取codeMsg
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年2月6日 上午9:08:34
	 */
	@RequestMapping(value = "/codeMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData codeMsg() {
		User user = LoginInterceptor.getCurrentUser();
		if(DataUtils.isNull(user)) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}
		
		String codeMsg = VerificationUtils.createCodeMsg();
		buf.codeMsgMap.put(codeMsg, user);
		return AsyncResponseData.getSuccess(codeMsg);
	}
	
}
