package com.jy.moudles.systemConfig.controller;

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
import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.moudles.systemConfig.service.SystemConfigService;

/** 
 * SystemConfig实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value="/systemconfig")
public class SystemConfigController {
	
	@Autowired
	private SystemConfigService systemconfigService;
	
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);
	
	/**
	 * 新增systemconfig对象
	 * 
	 * @param systemconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSystemConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSystemConfig(SystemConfig systemconfig) throws Exception{
		logger.info("新增SystemConfig Start");
		
		systemconfigService.insertSystemConfig(systemconfig);
		
		logger.info("新增SystemConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改systemconfig对象
	 * 
	 * @param systemconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSystemConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSystemConfig(SystemConfig systemconfig) throws Exception{
		logger.info("修改SystemConfig Start");
		
		systemconfigService.updateSystemConfig(systemconfig);
		
		logger.info("修改SystemConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除systemconfig对象
	 * 
	 * @param systemconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSystemConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSystemConfig(SystemConfig systemconfig) throws Exception{
		logger.info("删除SystemConfig Start");
		
		systemconfigService.deleteSystemConfigById(systemconfig.getId());
		
		logger.info("删除SystemConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取systemconfig对象
	 * 
	 * @param systemconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySystemConfigs", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySystemConfigs(SystemConfig systemconfig) throws Exception{
		logger.info("获取SystemConfig Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<SystemConfig> systemconfigs= systemconfigService.querySystemConfigsFilter(filter);
		logger.info("获取SystemConfig End");
		
		return AsyncResponseData.getSuccess(systemconfigs);
	}
	
	/**
	 * 根据ID获取systemconfig对象
	 * 
	 * @param systemconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSystemConfigById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSystemConfigById(String id) throws Exception{
		logger.info("获取SystemConfig Start");
		
		SystemConfig systemconfig = new SystemConfig();
		
		systemconfig = systemconfigService.getSystemConfigById(id);
		
		logger.info("获取SystemConfig End");
		
		return AsyncResponseData.getSuccess(systemconfig);
	}
	
}
