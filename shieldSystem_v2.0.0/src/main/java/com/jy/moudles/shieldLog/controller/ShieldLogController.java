package com.jy.moudles.shieldLog.controller;

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
import com.jy.moudles.shieldLog.entity.ShieldLog;
import com.jy.moudles.shieldLog.service.ShieldLogService;

/** 
 * shieldLog实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shieldlog")
public class ShieldLogController {
	
	@Autowired
	private ShieldLogService shieldlogService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldLogController.class);
	
	/**
	 * 新增shieldlog对象
	 * 
	 * @param shieldlog
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldLog", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldLog(ShieldLog shieldlog) throws Exception{
		logger.info("新增ShieldLog Start");
		
		shieldlogService.insertShieldLog(shieldlog);
		
		logger.info("新增ShieldLog End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shieldlog对象
	 * 
	 * @param shieldlog
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldLog", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldLog(ShieldLog shieldlog) throws Exception{
		logger.info("修改ShieldLog Start");
		
		shieldlogService.updateShieldLog(shieldlog);
		
		logger.info("修改ShieldLog End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shieldlog对象
	 * 
	 * @param shieldlog
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldLog", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldLog(ShieldLog shieldlog) throws Exception{
		logger.info("删除ShieldLog Start");
		
		shieldlogService.deleteShieldLogById(shieldlog.getId());
		
		logger.info("删除ShieldLog End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取shieldlog对象
	 * 
	 * @param shieldlog
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldLogs", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldLogs(ShieldLog shieldlog) throws Exception{
		logger.info("获取ShieldLog Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ShieldLog> shieldlogs= shieldlogService.queryShieldLogsFilter(filter);
		logger.info("获取ShieldLog End");
		
		return AsyncResponseData.getSuccess(shieldlogs);
	}
	
	/**
	 * 根据ID获取shieldlog对象
	 * 
	 * @param shieldlog
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldLogById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldLogById(String id) throws Exception{
		logger.info("获取ShieldLog Start");
		
		ShieldLog shieldlog = new ShieldLog();
		
		shieldlog = shieldlogService.getShieldLogById(id);
		
		logger.info("获取ShieldLog End");
		
		return AsyncResponseData.getSuccess(shieldlog);
	}
	
}
