package com.jy.common.ehcache.schedule;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jy.common.ehcache.ConfigEhcacheUtil;
import com.jy.common.ehcache.DeviceEhcacheUtil;
import com.jy.moudles.clockConfig.entity.ClockConfig;
import com.jy.moudles.clockConfig.service.ClockConfigService;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

import net.sf.ehcache.CacheManager;

@Component
public class EhcacheSchedule implements ApplicationListener<ContextRefreshedEvent>{
	
	private ClockDeviceService deivceService;
	
	private ClockConfigService configService;
	
	private CacheManager cacheManager;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null != event.getApplicationContext().getParent()) {
//			deivceService = event.getApplicationContext().getBean(ClockDeviceService.class);
//			configService = event.getApplicationContext().getBean(ClockConfigService.class);
//			cacheManager = (CacheManager) event.getApplicationContext().getBean("cacheManagerFactory");
//			updateEhcache();
		}
	}
	
	public void updateEhcache() {
		
		DeviceEhcacheUtil deviceUtil = new DeviceEhcacheUtil(cacheManager);
		ConfigEhcacheUtil configUtil = new ConfigEhcacheUtil(cacheManager);
		
		Map<String, Object> filter = new HashMap<String, Object>();
		deivceService.queryClockDevicesFilter(filter);
		
		for (ClockDevice clockDevice : deivceService.queryClockDevicesFilter(filter)) {
			deviceUtil.addClockDevice(clockDevice);
		}
		
		for (ClockConfig config : configService.queryClockConfigsFilter(filter)) {
			configUtil.addConfig(config);
		}
	}

}
