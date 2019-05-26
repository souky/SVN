package com.jy.moudles.clockLog.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.clockLog.VO.ClockLogQueryVO;
import com.jy.moudles.clockLog.entity.ClockLog;
import com.jy.moudles.clockLog.service.ClockLogService;

/** 
 * clockLog实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clocklog")
public class ClockLogController {
	
	@Autowired
	private ClockLogService clocklogService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockLogController.class);
	
	/**
	 * 新增clocklog对象
	 * 
	 * @param clocklog
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockLog", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockLog(ClockLog clocklog) throws Exception{
		logger.info("新增ClockLog Start");
		
		clocklogService.insertClockLog(clocklog);
		
		logger.info("新增ClockLog End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clocklog对象
	 * 
	 * @param clocklog
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockLog", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateClockLog(ClockLog clocklog) throws Exception{
		logger.info("修改ClockLog Start");
		
		clocklogService.updateClockLog(clocklog);
		
		logger.info("修改ClockLog End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clocklog对象
	 * 
	 * @param clocklog
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockLog", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClockLog(ClockLog clocklog) throws Exception{
		logger.info("删除ClockLog Start");
		
		clocklogService.deleteClockLogById(clocklog.getId());
		
		logger.info("删除ClockLog End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clocklog对象
	 * 
	 * @param clocklog
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockLogs", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockLogs(ClockLogQueryVO clocklog) throws Exception{
		logger.info("获取ClockLog Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		String operationUserName = clocklog.getOperationUserName();
		String operationInfo = clocklog.getOperationInfo();
		Integer operationType = clocklog.getOperationType();
		
		if (StringUtils.isNotBlank(operationUserName)) {
			filter.put("operationUserName", operationUserName);
		}
		if (null != operationType && 0 != operationType) {
			filter.put("operationType", operationType);
		}
		if (StringUtils.isNotBlank(operationInfo)) {
			filter.put("operationInfo", operationInfo);
		}
		
		PageHelper.startPage(clocklog.getPageNum(), clocklog.getPageSize());
		
		PageInfo<ClockLog> clockLogs = new PageInfo<ClockLog>(clocklogService.queryClockLogsFilter(filter));
		
		logger.info("获取ClockLog End");
		
		return AsyncResponseData.getSuccess(clockLogs);
	}
	
	/**
	 * 根据ID获取clocklog对象
	 * 
	 * @param clocklog
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockLogById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockLogById(String id) throws Exception{
		logger.info("获取ClockLog Start");
		
		ClockLog clocklog = new ClockLog();
		
		clocklog = clocklogService.getClockLogById(id);
		
		logger.info("获取ClockLog End");
		
		return AsyncResponseData.getSuccess(clocklog);
	}
	
}
