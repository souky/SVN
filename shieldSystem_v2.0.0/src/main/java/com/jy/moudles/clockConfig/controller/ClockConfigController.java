package com.jy.moudles.clockConfig.controller;

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
import com.jy.common.persistence.annotation.logAspect.JRYLogging;
import com.jy.common.persistence.annotation.logAspect.OpType;
import com.jy.moudles.clockConfig.entity.ClockConfig;
import com.jy.moudles.clockConfig.service.ClockConfigService;

/** 
 * ClockConfig实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clockconfig")
public class ClockConfigController {
	
	@Autowired
	private ClockConfigService clockconfigService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockConfigController.class);
	
	/**
	 * 新增clockconfig对象
	 * 
	 * @param clockconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockConfig", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.ADD, opInfo = "新增系统参数：{1}。", opReplaceParams = {"sysKey"})
	public AsyncResponseData.ResultData saveClockConfig(ClockConfig clockconfig) throws Exception{
		logger.info("新增ClockConfig Start");
		
		clockconfigService.insertClockConfig(clockconfig);
		
		logger.info("新增ClockConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clockconfig对象
	 * 
	 * @param clockconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockConfig", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.EDIT, opInfo = "修改系统参数：{1}。", opReplaceParams = {"sysKey"})
	public AsyncResponseData.ResultData updateClockConfig(ClockConfig clockconfig) throws Exception{
		logger.info("修改ClockConfig Start");
		
		clockconfigService.updateClockConfig(clockconfig);
		
		logger.info("修改ClockConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clockconfig对象
	 * 
	 * @param clockconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClockConfig(ClockConfig clockconfig) throws Exception{
		logger.info("删除ClockConfig Start");
		
		clockconfigService.deleteClockConfigByKey(clockconfig.getId());
		
		logger.info("删除ClockConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clockconfig对象
	 * 
	 * @param clockconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockConfigs", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockConfigs() throws Exception{
		logger.info("获取ClockConfig Start");
		
		List<ClockConfig> clockconfigs = clockconfigService.queryClockConfigsFilter(null);
		
		// 状态封装
		Map<String,Object> maps = new HashMap<>();
		if(null != clockconfigs && clockconfigs.size() > 0) {
			for(ClockConfig e : clockconfigs) {
				maps.put(e.getSysKey(), e.getSysVal());
			}
		}
		
		logger.info("获取ClockConfig End");
		
		return AsyncResponseData.getSuccess(maps);
	}
	
	/**
	 * 根据ID获取clockconfig对象
	 * 
	 * @param clockconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockConfigByKey", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockConfigById(String key) throws Exception{
		logger.info("获取ClockConfig Start");
		
		ClockConfig clockconfig = clockconfigService.getClockConfigByKey(key);
		
		logger.info("获取ClockConfig End");
		
		return AsyncResponseData.getSuccess(clockconfig);
	}
	
}
