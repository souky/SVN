package com.jy.moudles.region.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.region.entity.Region;
import com.jy.moudles.region.service.RegionService;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * region实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/region")
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegionController.class);
	

	/**
	 * 获取region对象
	 * 
	 * @param regionPId
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRegionsByParentId", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryRegions(String regionPId) throws Exception{
		LOGGER.info("获取Region Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("regionPId",regionPId);
		List<Region> regions= regionService.queryRegionsFilter(filter);
		LOGGER.info("获取Region End");
		
		return AsyncResponseData.getSuccess(regions);
	}
	
}
