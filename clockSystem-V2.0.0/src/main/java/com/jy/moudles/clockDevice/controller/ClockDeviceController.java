package com.jy.moudles.clockDevice.controller;

import java.util.ArrayList;
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
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

/** 
 * clockDevice实现类
 *
 * 创建人：1
 * 创建时间：2018-03-14
 */
@Controller
@RequestMapping(value="/clockdevice")
public class ClockDeviceController {
	
	@Autowired
	private ClockDeviceService clockdeviceService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockDeviceController.class);
	
	/**
	 * 新增clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockDevice(ClockDevice clockdevice) throws Exception{
		logger.info("新增ClockDevice Start");
		
		clockdeviceService.insertClockDevice(clockdevice);
		
		logger.info("新增ClockDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateClockDevice(ClockDevice clockdevice) throws Exception{
		logger.info("修改ClockDevice Start");
		
		clockdeviceService.updateClockDevice(clockdevice);
		
		logger.info("修改ClockDevice End");
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
	public AsyncResponseData.ResultData deleteClockDevice(ClockDevice clockdevice) throws Exception{
		logger.info("删除ClockDevice Start");
		
		clockdeviceService.deleteClockDeviceById(clockdevice.getId());
		
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
	public AsyncResponseData.ResultData deleteClockDevices(HttpServletRequest request) throws Exception{
		logger.info("删除ClockDevice Start");
		
		String[] idString = request.getParameterValues("idString");
		
		List<String> ids = new ArrayList<>();
		for(String s : idString){
			ids.add(s);
		}
		clockdeviceService.deleteClockDevices(ids);
		
		logger.info("删除ClockDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 通过id获取clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockDeviceById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockDeviceById(String id) throws Exception{
		logger.info("删除ClockDevice Start");
		
		ClockDevice clockdevice = clockdeviceService.getClockDeviceById(id);
		
		logger.info("删除ClockDevice End");
		return AsyncResponseData.getSuccess(clockdevice);
	}
	
	/**
	 * 获取clockdevice对象
	 * 
	 * @param clockdevice
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockDevices(ClockDevice clockdevice,int pageNum,int pageSize) throws Exception{
		logger.info("获取ClockDevice Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		String ip = clockdevice.getIp();
		String port = clockdevice.getPort();
		String address = clockdevice.getAddress();
		String remark = clockdevice.getRemark();
		
		if(StringUtils.isNotBlank(ip)) {
			filter.put("ip",ip);
		}
		if(StringUtils.isNotBlank(port)) {
			filter.put("port",port);
		}
		if(StringUtils.isNotBlank(address)) {
			filter.put("address",address);
		}
		if(StringUtils.isNotBlank(remark)) {
			filter.put("remark",remark);
		}
		
		PageHelper.startPage(pageNum, pageSize);
    	PageInfo<ClockDevice> clockdevices = new PageInfo<ClockDevice>(clockdeviceService.queryClockDevicesFilter(filter));
		logger.info("获取ClockDevice End");
		
		return AsyncResponseData.getSuccess(clockdevices);
	}
	
	/**
	 * 根据orderList获取clockdevice对象
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockDevicesByOrderList", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockDevicesByOrderList() throws Exception{
		logger.info("修改ClockDevice Start");
		
		List<ClockDevice> clockDevices = clockdeviceService.queryClockDevicesByOrderList();
		
		logger.info("修改ClockDevice End");
		return AsyncResponseData.getSuccess(clockDevices);
	}
	
	/**
	 * 查询未绑定的clockdevice对象
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIdleClockDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getIdleClockDevices() throws Exception{
		logger.info("修改ClockDevice Start");
		
		List<ClockDevice> clockDevices = clockdeviceService.getIdleClockDevices();
		
		logger.info("修改ClockDevice End");
		return AsyncResponseData.getSuccess(clockDevices);
	}
	
	/**
	 * 解绑clockdevice对象
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/bootDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData bootDevice(String id) throws Exception{
		logger.info("修改ClockDevice Start");
		
		clockdeviceService.bootDevice(id);
		
		logger.info("修改ClockDevice End");
		return AsyncResponseData.getSuccess();
	}
	
}
