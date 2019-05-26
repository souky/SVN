package com.jy.moudles.clockDevice.schedule;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jy.moudles.clockConfig.entity.ClockConfig;
import com.jy.moudles.clockConfig.service.ClockConfigService;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;
import com.jy.moudles.clockSat.service.ClockSatService;

import net.sf.ehcache.CacheManager;

@Component
public class InsertOrUpdateDeviceSchedule implements ApplicationListener<ContextRefreshedEvent> {
	
	@SuppressWarnings("unused")
	private CacheManager cacheManager;
	
	@Autowired
	private ClockDeviceService deviceService;
	
	@Autowired
	private ClockConfigService configService;
	
	@SuppressWarnings("unused")
	@Autowired
	private ClockSatService satService;
	
	@Scheduled(fixedDelay = 5000)
	public void insertOrUpdateDeviceSchedule() {
		
//		SatUtil sat = SatUtil.getInstance();
//		
//		List<SatInfo> satInfos = sat.getSatInfo();
//		
//		for (SatInfo satInfo : satInfos) {
//			
//			try {
//				ClockSat clockSat = null;
//				
//				Map<String, Object> filter = new HashMap<String, Object>();
//				filter.put("satNo", satInfo.getSatNo());
//				List<ClockSat> sats= satService.queryClockSatsFilter(filter);
//				
//				if (null == sats || 0 == sats.size()) {
//					clockSat = new ClockSat();
//					clockSat.setId(UUIDUtil.get32UUID());
//					clockSat.setCreateDate(new Date());
//					clockSat.setCreateUser("服务器");
//					clockSat.setUpdateDate(new Date());
//					clockSat.setUpdateUser("服务器");
//				} else {
//					clockSat = sats.get(0);
//					clockSat.setUpdateDate(new Date());
//					clockSat.setUpdateUser("服务器");
//				}
//				
//				copySatInfo(satInfo, clockSat);
//				
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//			
//		}
		
//		DeviceEhcacheUtil util = new DeviceEhcacheUtil(cacheManager);
		Map<String, Object> filter = new HashMap<>();
		List<ClockDevice> devices = deviceService.queryClockDevicesFilter(filter);
		boolean selfExam = true;
		for (ClockDevice device : devices) {
			System.out.println("系统---更新时钟状态代码---更新时钟状态前是" + device.getClockStatus());
			if (4 == device.getClockStatus()) {
				selfExam = false;
			}
			
			// 暂时由更新时间代替
			if (null != device.getUpdateDate()) {
				Date date = device.getUpdateDate();
				Date now = new Date();
				if (4 != device.getClockStatus() && 3 != device.getClockStatus()) {
					if (null != date
							&& ((60000 < now.getTime() - date.getTime() && 600000 >= now.getTime() - date.getTime())
									|| 0 > now.getTime() - date.getTime())) {
						// 时间异常
						device.setClockStatus(2);
					} else if (600000 < now.getTime() - date.getTime()) {
						// 离线
						device.setClockStatus(1);
					} else {
						device.setClockStatus(0);
					}
				}
			} else {
				// 离线
				device.setClockStatus(1);
			}
			if (selfExam) {
				ClockConfig config = new ClockConfig();
				config.setSysKey("selfExam");
				config.setSysVal("0");
				configService.updateClockConfig(config);
			}
			System.out.println("系统---更新时钟状态代码---更新时钟状态是" + device.getClockStatus());
			deviceService.updateClockDevice(device);
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null != event.getApplicationContext().getParent()) {
			cacheManager = (CacheManager) event.getApplicationContext().getBean("cacheManagerFactory");
		}
	}
	
	@SuppressWarnings("unused")
	private void copySatInfo(Object src, Object dst) throws IllegalArgumentException, IllegalAccessException {
		
		Field[] fieldSrcs = src.getClass().getDeclaredFields();
		Field[] fieldDsts = dst.getClass().getDeclaredFields();
		
		for (Field fieldsrc : fieldSrcs) {
			fieldsrc.setAccessible(true);
			if (null == fieldsrc.get(src)) {
				continue;
			}
			
			for (Field fieldDst : fieldDsts) {
				fieldDst.setAccessible(true);
				String fieldSrcName = fieldsrc.getName().toLowerCase();
				String fieldDstName = fieldDst.getName().toLowerCase();
				if (fieldSrcName.equals(fieldDstName) || fieldSrcName.contains(fieldDstName) || fieldDstName.contains(fieldSrcName)) {
					fieldDst.set(dst, fieldsrc.get(src));
					break;
				}
			}
		}
	}

}
