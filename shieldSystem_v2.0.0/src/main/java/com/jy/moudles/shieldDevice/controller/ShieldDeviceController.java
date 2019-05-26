package com.jy.moudles.shieldDevice.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.jy.moudles.shieldDevice.VO.ShieldDeviceQueryVO;
import com.jy.moudles.shieldDevice.entity.ShieldDevice;
import com.jy.moudles.shieldDevice.service.ShieldDeviceService;

/** 
 * shieldDevice实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shielddevice")
public class ShieldDeviceController {
	
	@Autowired
	private ShieldDeviceService shielddeviceService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldDeviceController.class);
	
	/**
	 * 新增shielddevice对象
	 * 
	 * @param shielddevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldDevice(ShieldDevice shielddevice) throws Exception{
		logger.info("新增ShieldDevice Start");
		
		shielddeviceService.insertShieldDevice(shielddevice);
		
		logger.info("新增ShieldDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shielddevice对象
	 * 
	 * @param shielddevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldDevice(ShieldDevice shielddevice) throws Exception{
		logger.info("修改ShieldDevice Start");
		
		shielddeviceService.updateShieldDevice(shielddevice);
		
		logger.info("修改ShieldDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shielddevice对象
	 * 
	 * @param shielddevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldDevice(ShieldDevice shielddevice) throws Exception{
		logger.info("删除ShieldDevice Start");
		
		shielddeviceService.deleteShieldDeviceById(shielddevice.getId());
		
		logger.info("删除ShieldDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 批量删除shielddevice对象
	 * 
	 * @param shielddevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldDevices(String ids) throws Exception{
		logger.info("删除ShieldDevice Start");
		
		if(StringUtils.isBlank(ids)) {
			return AsyncResponseData.getSuccess().asParamError("请选择删除项");
		}
		String[] ids_array = ids.split(",");
		List<String> list = Arrays.asList(ids_array);
		shielddeviceService.deleteShieldDevices(list);
		
		logger.info("删除ShieldDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	
	
	/**
	 * 获取shielddevice对象
	 * 
	 * @param shielddevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldDevices(ShieldDeviceQueryVO shielddevice) throws Exception{
		logger.info("获取ShieldDevice Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		String shieldIpAddr = shielddevice.getShieldIpAddr();
		Integer shieldStatus = shielddevice.getShieldStatus();
		String physicalExamRoom = shielddevice.getPhysicalExamRoom();
		String logicalExamRoom = shielddevice.getLogicalExamRoom();

		if (StringUtils.isNotBlank(shieldIpAddr)) {
			filter.put("shieldIpAddr", shieldIpAddr);
		}
		if (null != shieldStatus && -1 != shieldStatus) {
			filter.put("shieldStatus", shieldStatus);
		}
		if (StringUtils.isNotBlank(logicalExamRoom)) {
			filter.put("logicalExamRoom", logicalExamRoom);
		}
		if (StringUtils.isNotBlank(physicalExamRoom)) {
			filter.put("physicalExamRoom", physicalExamRoom);
		}
		
		PageHelper.startPage(shielddevice.getPageNum(), shielddevice.getPageSize());
		
		PageInfo<ShieldDevice> shielddevices = new PageInfo<ShieldDevice>(shielddeviceService.queryShieldDevicesFilterWithPosition(filter));
		
		logger.info("获取ShieldDevice End");
		
		return AsyncResponseData.getSuccess(shielddevices);
	}
	
	/**
	 * 根据ID获取shielddevice对象
	 * 
	 * @param shielddevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldDeviceById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldDeviceById(String id) throws Exception{
		logger.info("获取ShieldDevice Start");
		
		ShieldDevice shielddevice = new ShieldDevice();
		
		shielddevice = shielddeviceService.getShieldDeviceById(id);
		
		logger.info("获取ShieldDevice End");
		
		return AsyncResponseData.getSuccess(shielddevice);
	}
	
}
