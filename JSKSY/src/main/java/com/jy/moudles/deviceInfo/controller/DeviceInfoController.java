package com.jy.moudles.deviceInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jy.moudles.deviceInfo.entity.DeviceInfo;
import com.jy.moudles.deviceInfo.service.DeviceInfoService;
import com.jy.moudles.deviceInfo.utils.DeviceUtil;

@Controller
public class DeviceInfoController {

	@Autowired
	private DeviceInfoService deviceInfoService;
	
	@RequestMapping(value="/anticheat/updateDevice",method = RequestMethod.POST)
	public void updateDeviceInfo(String deviceId) {
		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
		if(null != deviceInfo) {
			DeviceUtil.setCheatDeviceInfo(deviceInfo);
		}
	}
	
}
