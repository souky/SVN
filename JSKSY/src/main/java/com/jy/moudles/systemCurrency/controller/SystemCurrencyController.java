package com.jy.moudles.systemCurrency.controller;

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
import com.jy.moudles.systemCurrency.entity.SystemCurrency;
import com.jy.moudles.systemCurrency.service.SystemCurrencyService;

/** 
 * SystemCurrency实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value="/systemcurrency")
public class SystemCurrencyController {
	
	@Autowired
	private SystemCurrencyService systemcurrencyService;
	
	private static final Logger logger = LoggerFactory.getLogger(SystemCurrencyController.class);
	
	/**
	 * 新增systemcurrency对象
	 * 
	 * @param systemcurrency
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSystemCurrency", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSystemCurrency(SystemCurrency systemcurrency) throws Exception{
		logger.info("新增SystemCurrency Start");
		
		systemcurrencyService.insertSystemCurrency(systemcurrency);
		
		logger.info("新增SystemCurrency End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改systemcurrency对象
	 * 
	 * @param systemcurrency
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSystemCurrency", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSystemCurrency(SystemCurrency systemcurrency) throws Exception{
		logger.info("修改SystemCurrency Start");
		
		systemcurrencyService.updateSystemCurrency(systemcurrency);
		
		logger.info("修改SystemCurrency End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除systemcurrency对象
	 * 
	 * @param systemcurrency
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSystemCurrency", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSystemCurrency(SystemCurrency systemcurrency) throws Exception{
		logger.info("删除SystemCurrency Start");
		
		systemcurrencyService.deleteSystemCurrencyById(systemcurrency.getId());
		
		logger.info("删除SystemCurrency End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取systemcurrency对象
	 * 
	 * @param systemcurrency
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySystemCurrencys", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySystemCurrencys(SystemCurrency systemcurrency) throws Exception{
		logger.info("获取SystemCurrency Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<SystemCurrency> systemcurrencys= systemcurrencyService.querySystemCurrencysFilter(filter);
		logger.info("获取SystemCurrency End");
		
		return AsyncResponseData.getSuccess(systemcurrencys);
	}
	
	/**
	 * 根据ID获取systemcurrency对象
	 * 
	 * @param systemcurrency
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSystemCurrencyById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSystemCurrencyById(String id) throws Exception{
		logger.info("获取SystemCurrency Start");
		
		SystemCurrency systemcurrency = new SystemCurrency();
		
		systemcurrency = systemcurrencyService.getSystemCurrencyById(id);
		
		logger.info("获取SystemCurrency End");
		
		return AsyncResponseData.getSuccess(systemcurrency);
	}
	
}
