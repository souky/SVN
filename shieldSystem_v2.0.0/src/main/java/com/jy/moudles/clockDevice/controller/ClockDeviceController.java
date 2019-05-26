package com.jy.moudles.clockDevice.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.jy.common.persistence.annotation.logAspect.JRYLogging;
import com.jy.common.persistence.annotation.logAspect.OpType;
import com.jy.moudles.clockDevice.VO.ClockDeviceQueryVO;
import com.jy.moudles.clockDevice.VO.CountVO;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;
import com.jy.moudles.clockDevicePosition.entity.ClockDevicePosition;
import com.jy.moudles.clockDevicePosition.service.ClockDevicePositionService;

/** 
 * ClockDevice实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clockdevice")
public class ClockDeviceController {
	
	@Autowired
	private ClockDeviceService clockdeviceService;
	@Autowired
	private ClockDevicePositionService clockdevicePositionService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockDeviceController.class);
	
	/**
	 * 新增clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockDevice(ClockDevice clockdevice, HttpServletRequest request) throws Exception{
		logger.info("新增ClockDevice Start");
		
		clockdeviceService.insertClockDevice(clockdevice);
		logger.info("新增ClockDevice End");
		//LogUtils.addLog("时钟设备", "新增时钟设备："+clockdevice.getId(), LogUtils.ADD, request);
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 统计信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounts", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getCounts() throws Exception{
		logger.info("统计信息 Start");
		List<CountVO> list = clockdeviceService.getClockStatusCount();
		
		Map<String,Object> maps = new HashMap<>();
		
		if(null != list && list.size() > 0) {
			for(CountVO e : list) {
				maps.put(e.getClockStatus(), e.getCount()==null?0:e.getCount());
			}
		}
		logger.info("统计信息 End");
		return AsyncResponseData.getSuccess(maps);
	}
	
	/**
	 * 修改clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockDevice", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.EDIT, opInfo = "IP:{1}，MAC{2}:时钟设备。", opReplaceParams = {"clockIpAddr, clockMac"})
	public AsyncResponseData.ResultData updateClockDevice(ClockDevice clockdevice, HttpServletRequest request) throws Exception{
		logger.info("修改ClockDevice Start");
		
		clockdeviceService.updateClockDevice(clockdevice);
		
		//地址信息修改
		String logical = clockdevice.getLogicalExamRoom();
		String physical = clockdevice.getPhysicalExamRoom();
		ClockDevicePosition clockDevicePosition = clockdevicePositionService.getClockDevicePositionByClockId(clockdevice.getId());
		// 判断是否存在地址信息
		if(null == clockDevicePosition) {
			//不存在   增加地址信息
			clockDevicePosition = new ClockDevicePosition();
			clockDevicePosition.setClockId(clockdevice.getId());
			clockDevicePosition.setLogicalExamRoom(logical);
			clockDevicePosition.setPhysicalExamRoom(physical);
			clockdevicePositionService.insertClockDevicePosition(clockDevicePosition);
		}else {
			//存在  修改地址信息
			clockDevicePosition.setLogicalExamRoom(logical);
			clockDevicePosition.setPhysicalExamRoom(physical);
			clockdevicePositionService.updateClockDevicePosition(clockDevicePosition);
		}
		
		logger.info("修改ClockDevice End");
		//LogUtils.addLog("时钟设备", "修改时钟设备："+clockdevice.getId(), LogUtils.UPDATE, request);
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockDevice", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.DEL, opInfo = "ID:{1}时钟设备。", opReplaceParams = {"id"})
	public AsyncResponseData.ResultData deleteClockDevice(ClockDevice clockdevice, HttpServletRequest request) throws Exception{
		logger.info("删除ClockDevice Start");
		
		clockdeviceService.deleteClockDeviceById(clockdevice.getId());
		//对应删除考场地址
		ClockDevicePosition clockDevicePosition = clockdevicePositionService.getClockDevicePositionByClockId(clockdevice.getId());
		if(null != clockDevicePosition) {
			clockdevicePositionService.deleteClockDevicePositionById(clockDevicePosition.getId());
		}
		//LogUtils.addLog("时钟设备", "删除时钟设备："+clockdevice.getId(), LogUtils.DELETE, request);
		logger.info("删除ClockDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockDevices", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.DEL, opInfo = "ID:{1}时钟设备。", opReplaceParams = {"ids"})
	public AsyncResponseData.ResultData deleteClockDevices(String ids, HttpServletRequest request) throws Exception{
		logger.info("删除ClockDevice Start");
		
		if(StringUtils.isBlank(ids)) {
			
		}
		String[] ids_array = ids.split(",");
		List<String> list = Arrays.asList(ids_array);
		clockdeviceService.deleteClockDevices(list);
		//对应删除考场地址
		clockdevicePositionService.deleteClockDevicePositions(list);
		
		//LogUtils.addLog("时钟设备", "批量删除时钟设备："+ids, LogUtils.DELETE, request);
		logger.info("删除ClockDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockDevices(ClockDeviceQueryVO clockdevice) throws Exception{
		logger.info("获取ClockDevice Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();

		String clockIpAddr = clockdevice.getClockIpAddr();
		Integer clockStatus = clockdevice.getClockStatus();
		String physicalExamRoom = clockdevice.getPhysicalExamRoom();
		String logicalExamRoom = clockdevice.getLogicalExamRoom();

		if (StringUtils.isNotBlank(clockIpAddr)) {
			filter.put("clockIpAddr", clockIpAddr);
		}
		if (null != clockStatus && -1 != clockStatus) {
			filter.put("clockStatus", clockStatus);
		}
		if (StringUtils.isNotBlank(logicalExamRoom)) {
			filter.put("logicalExamRoom", logicalExamRoom);
		}
		if (StringUtils.isNotBlank(physicalExamRoom)) {
			filter.put("physicalExamRoom", physicalExamRoom);
		}
		

		PageHelper.startPage(clockdevice.getPageNum(), clockdevice.getPageSize());
		PageInfo<ClockDevice> clockdevices = new PageInfo<ClockDevice>(
				clockdeviceService.queryClockDevicesFilterWithPosition(filter));
		logger.info("获取ClockDevice End");
		return AsyncResponseData.getSuccess(clockdevices);
	}
	
	/**
	 * 根据ID获取clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnbindingClockDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getUnbindingClockDevice() throws Exception{
		logger.info("获取ClockDevice Start");
		
		List<ClockDevice> clockdevices = clockdeviceService.getUnBindingClockDevice(null);
		
		logger.info("获取ClockDevice End");
		
		return AsyncResponseData.getSuccess(clockdevices);
	}
	
	/**
	 * 根据ID获取clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockDeviceById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockDeviceById(String id) throws Exception{
		logger.info("获取ClockDevice Start");
		
		ClockDevice clockdevice = new ClockDevice();
		
		clockdevice = clockdeviceService.getClockDeviceById(id);
		
		logger.info("获取ClockDevice End");
		
		return AsyncResponseData.getSuccess(clockdevice);
	}
	
}
