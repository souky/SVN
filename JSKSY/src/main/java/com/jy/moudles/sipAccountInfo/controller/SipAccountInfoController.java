package com.jy.moudles.sipAccountInfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.sipAccountInfo.entity.SipAccountInfo;
import com.jy.moudles.sipAccountInfo.service.SipAccountInfoService;

/** 
 * SipAccountInfo实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@Controller
@RequestMapping(value="/sipaccountinfo")
public class SipAccountInfoController {
	
	@Autowired
	private SipAccountInfoService sipaccountinfoService;
	
	private static final Logger logger = LoggerFactory.getLogger(SipAccountInfoController.class);
	
	/**
	 * 新增sipaccountinfo对象
	 * 
	 * @param sipaccountinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSipAccountInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSipAccountInfo(SipAccountInfo sipaccountinfo) throws Exception{
		logger.info("新增SipAccountInfo Start");
		
		sipaccountinfoService.insertSipAccountInfo(sipaccountinfo);
		
		logger.info("新增SipAccountInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改sipaccountinfo对象
	 * 
	 * @param sipaccountinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSipAccountInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSipAccountInfo(SipAccountInfo sipaccountinfo) throws Exception{
		logger.info("修改SipAccountInfo Start");
		
		sipaccountinfoService.updateSipAccountInfo(sipaccountinfo);
		
		logger.info("修改SipAccountInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除sipaccountinfo对象
	 * 
	 * @param sipaccountinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSipAccountInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSipAccountInfo(SipAccountInfo sipaccountinfo) throws Exception{
		logger.info("删除SipAccountInfo Start");
		
		sipaccountinfoService.deleteSipAccountInfoById(sipaccountinfo.getId());
		
		logger.info("删除SipAccountInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取sipaccountinfo对象
	 * 
	 * @param sipaccountinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySipAccountInfos", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySipAccountInfos(SipAccountInfo sipaccountinfo) throws Exception{
		logger.info("获取SipAccountInfo Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<SipAccountInfo> sipaccountinfos= sipaccountinfoService.querySipAccountInfosFilter(filter);
		logger.info("获取SipAccountInfo End");
		
		return AsyncResponseData.getSuccess(sipaccountinfos);
	}
	
	/**
	 * 根据ID获取sipaccountinfo对象
	 * 
	 * @param sipaccountinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSipAccountInfoById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSipAccountInfoById(String id) throws Exception{
		logger.info("获取SipAccountInfo Start");
		
		SipAccountInfo sipaccountinfo = new SipAccountInfo();
		
		sipaccountinfo = sipaccountinfoService.getSipAccountInfoById(id);
		
		logger.info("获取SipAccountInfo End");
		
		return AsyncResponseData.getSuccess(sipaccountinfo);
	}
	
}
