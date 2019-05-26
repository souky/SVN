package com.jy.moudles.cheatFrequencyinfo.controller;

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
import com.jy.moudles.cheatFrequencyinfo.entity.CheatFrequencyinfo;
import com.jy.moudles.cheatFrequencyinfo.service.CheatFrequencyinfoService;

/** 
 * CheatFrequencyinfo实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-07
 */
@Controller
@RequestMapping(value="/cheatfrequencyinfo")
public class CheatFrequencyinfoController {
	
	@Autowired
	private CheatFrequencyinfoService cheatfrequencyinfoService;
	
	private static final Logger logger = LoggerFactory.getLogger(CheatFrequencyinfoController.class);
	
	/**
	 * 新增cheatfrequencyinfo对象
	 * 
	 * @param cheatfrequencyinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCheatFrequencyinfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveCheatFrequencyinfo(CheatFrequencyinfo cheatfrequencyinfo) throws Exception{
		logger.info("新增CheatFrequencyinfo Start");
		
		cheatfrequencyinfoService.insertCheatFrequencyinfo(cheatfrequencyinfo);
		
		logger.info("新增CheatFrequencyinfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改cheatfrequencyinfo对象
	 * 
	 * @param cheatfrequencyinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCheatFrequencyinfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateCheatFrequencyinfo(CheatFrequencyinfo cheatfrequencyinfo) throws Exception{
		logger.info("修改CheatFrequencyinfo Start");
		
		cheatfrequencyinfoService.updateCheatFrequencyinfo(cheatfrequencyinfo);
		
		logger.info("修改CheatFrequencyinfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除cheatfrequencyinfo对象
	 * 
	 * @param cheatfrequencyinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCheatFrequencyinfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteCheatFrequencyinfo(CheatFrequencyinfo cheatfrequencyinfo) throws Exception{
		logger.info("删除CheatFrequencyinfo Start");
		
		cheatfrequencyinfoService.deleteCheatFrequencyinfoById(cheatfrequencyinfo.getId());
		
		logger.info("删除CheatFrequencyinfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取cheatfrequencyinfo对象
	 * 
	 * @param cheatfrequencyinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCheatFrequencyinfos", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryCheatFrequencyinfos(CheatFrequencyinfo cheatfrequencyinfo) throws Exception{
		logger.info("获取CheatFrequencyinfo Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<CheatFrequencyinfo> cheatfrequencyinfos= cheatfrequencyinfoService.queryCheatFrequencyinfosFilter(filter);
		logger.info("获取CheatFrequencyinfo End");
		
		return AsyncResponseData.getSuccess(cheatfrequencyinfos);
	}
	
	/**
	 * 根据ID获取cheatfrequencyinfo对象
	 * 
	 * @param cheatfrequencyinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCheatFrequencyinfoById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getCheatFrequencyinfoById(String id) throws Exception{
		logger.info("获取CheatFrequencyinfo Start");
		
		CheatFrequencyinfo cheatfrequencyinfo = new CheatFrequencyinfo();
		
		cheatfrequencyinfo = cheatfrequencyinfoService.getCheatFrequencyinfoById(id);
		
		logger.info("获取CheatFrequencyinfo End");
		
		return AsyncResponseData.getSuccess(cheatfrequencyinfo);
	}
	
}
