package com.jy.moudles.shieldDevicePosition.controller;

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
import com.jy.moudles.shieldDevicePosition.entity.ShieldDevicePosition;
import com.jy.moudles.shieldDevicePosition.service.ShieldDevicePositionService;

/** 
 * shieldDevicePosition实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shielddeviceposition")
public class ShieldDevicePositionController {
	
	@Autowired
	private ShieldDevicePositionService shielddevicepositionService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldDevicePositionController.class);
	
	/**
	 * 新增shielddeviceposition对象
	 * 
	 * @param shielddeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldDevicePosition", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldDevicePosition(ShieldDevicePosition shielddeviceposition) throws Exception{
		logger.info("新增ShieldDevicePosition Start");
		
		shielddevicepositionService.insertShieldDevicePosition(shielddeviceposition);
		
		logger.info("新增ShieldDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shielddeviceposition对象
	 * 
	 * @param shielddeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldDevicePosition", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldDevicePosition(ShieldDevicePosition shielddeviceposition) throws Exception{
		logger.info("修改ShieldDevicePosition Start");
		
		shielddevicepositionService.updateShieldDevicePosition(shielddeviceposition);
		
		logger.info("修改ShieldDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shielddeviceposition对象
	 * 
	 * @param shielddeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldDevicePosition", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldDevicePosition(ShieldDevicePosition shielddeviceposition) throws Exception{
		logger.info("删除ShieldDevicePosition Start");
		
		shielddevicepositionService.deleteShieldDevicePositionById(shielddeviceposition.getId());
		
		logger.info("删除ShieldDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取shielddeviceposition对象
	 * 
	 * @param shielddeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldDevicePositions", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldDevicePositions(ShieldDevicePosition shielddeviceposition) throws Exception{
		logger.info("获取ShieldDevicePosition Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ShieldDevicePosition> shielddevicepositions= shielddevicepositionService.queryShieldDevicePositionsFilter(filter);
		logger.info("获取ShieldDevicePosition End");
		
		return AsyncResponseData.getSuccess(shielddevicepositions);
	}
	
	/**
	 * 根据ID获取shielddeviceposition对象
	 * 
	 * @param shielddeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldDevicePositionById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldDevicePositionById(String id) throws Exception{
		logger.info("获取ShieldDevicePosition Start");
		
		ShieldDevicePosition shielddeviceposition = new ShieldDevicePosition();
		
		shielddeviceposition = shielddevicepositionService.getShieldDevicePositionById(id);
		
		logger.info("获取ShieldDevicePosition End");
		
		return AsyncResponseData.getSuccess(shielddeviceposition);
	}
	
}
