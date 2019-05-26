package com.jy.moudles.clockSat.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.clockSat.entity.ClockSat;
import com.jy.moudles.clockSat.service.ClockSatService;

/** 
 * clockSat实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clocksat")
public class ClockSatController {
	
	@Autowired
	private ClockSatService clocksatService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockSatController.class);
	
	/**
	 * 新增clocksat对象
	 * 
	 * @param clocksat
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockSat", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockSat(ClockSat clocksat) throws Exception{
		logger.info("新增ClockSat Start");
		
		clocksatService.insertClockSat(clocksat);
		
		logger.info("新增ClockSat End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clocksat对象
	 * 
	 * @param clocksat
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockSat", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateClockSat(ClockSat clocksat) throws Exception{
		logger.info("修改ClockSat Start");
		
		clocksatService.updateClockSat(clocksat);
		
		logger.info("修改ClockSat End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clocksat对象
	 * 
	 * @param clocksat
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockSat", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClockSat(ClockSat clocksat) throws Exception{
		logger.info("删除ClockSat Start");
		
		clocksatService.deleteClockSatById(clocksat.getId());
		
		logger.info("删除ClockSat End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clocksat对象
	 * 
	 * @param clocksat
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockSats", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockSats(ClockSat clocksat) throws Exception{
		logger.info("获取ClockSat Start");
		
		List<ClockSat> clocksats = clocksatService.queryClockSatsFilter(null);
		
		logger.info("获取ClockSat End");
		
		return AsyncResponseData.getSuccess(clocksats);
	}
	
	/**
	 * 根据ID获取clocksat对象
	 * 
	 * @param clocksat
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockSatById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockSatById(String id) throws Exception{
		logger.info("获取ClockSat Start");
		
		ClockSat clocksat = new ClockSat();
		
		clocksat = clocksatService.getClockSatById(id);
		
		logger.info("获取ClockSat End");
		
		return AsyncResponseData.getSuccess(clocksat);
	}
	
}
