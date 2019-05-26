package com.jy.moudles.satellite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.satellite.entity.Satellite;
import com.jy.moudles.satellite.service.SatelliteService;

/** 
 * Satellite实现类
 *
 * 创建人：1
 * 创建时间：2018-03-20
 */
@Controller
@RequestMapping(value="/satellite")
public class SatelliteController {
	
	@Autowired
	private SatelliteService satelliteService;
	
	private static final Logger logger = LoggerFactory.getLogger(SatelliteController.class);
	
	/**
	 * 新增satellite对象
	 * 
	 * @param satellite
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSatellite", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSatellite(Satellite satellite) throws Exception{
		logger.info("新增Satellite Start");
		
		satelliteService.insertSatellite(satellite);
		
		logger.info("新增Satellite End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改satellite对象
	 * 
	 * @param satellite
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSatellite", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSatellite(Satellite satellite) throws Exception{
		logger.info("修改Satellite Start");
		
		satelliteService.updateSatellite(satellite);
		
		logger.info("修改Satellite End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除satellite对象
	 * 
	 * @param satellite
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSatellite", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSatellite(Satellite satellite) throws Exception{
		logger.info("删除Satellite Start");
		
		satelliteService.deleteSatelliteById(satellite.getId());
		
		logger.info("删除Satellite End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取satellite对象
	 * 
	 * @param satellite
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySatellites", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySatellites(Satellite satellite) throws Exception{
		logger.info("获取Satellite Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<Satellite> satellites= satelliteService.querySatellitesFilter(filter);
		logger.info("获取Satellite End");
		
		return AsyncResponseData.getSuccess(satellites);
	}
	
	/**
	 * 根据ID获取satellite对象
	 * 
	 * @param satellite
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSatelliteById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSatelliteById(String id) throws Exception{
		logger.info("获取Satellite Start");
		
		Satellite satellite = new Satellite();
		
		satellite = satelliteService.getSatelliteById(id);
		
		logger.info("获取Satellite End");
		
		return AsyncResponseData.getSuccess(satellite);
	}
	
	/**
	 * 获取学校名称
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSchoolName", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSchoolName() throws Exception {

		ResourceBundle resource = ResourceBundle.getBundle("school");
		String schoolName = new String(resource.getString("schoolName").getBytes("ISO-8859-1"), "UTF8");

		return AsyncResponseData.getSuccess(schoolName);
	}
	
}
