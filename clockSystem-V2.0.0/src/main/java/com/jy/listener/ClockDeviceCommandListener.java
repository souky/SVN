package com.jy.listener;

import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jy.common.ehcache.DeviceEhcacheUtil;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

import net.sf.ehcache.CacheManager;

@Component
public class ClockDeviceCommandListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@SuppressWarnings("unused")
	private DeviceEhcacheUtil deviceUtil;
	
	@SuppressWarnings("unused")
	private CacheManager cacheManager;
	
	@Autowired
	private ClockDeviceService deviceService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (null != event.getApplicationContext().getParent()) {
			cacheManager = (CacheManager) event.getApplicationContext().getBean("cacheManagerFactory");
//			SatUtil util = SatUtil.getInstance();
//			util.startCatSatModuleData();
//			deviceUtil = new DeviceEhcacheUtil(cacheManager);
			new Thread(new GetClockDeviceThread()).start();
		}
	}
	
	private class GetClockDeviceThread implements Runnable {

		@Override
		public void run() {
			int listenPort = 31025;
			DatagramSocket responseSocket = null;
			try {
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				responseSocket = new DatagramSocket(listenPort);
				
				while (true) {
					responseSocket.receive(packet);
					
					ClockDevice clockDevice = new ClockDevice(packet.getAddress().getHostAddress(), packet.getData());
					ClockDevice cacheClock = null;
					Map<String, Object> filter = new HashMap<>();
					if (null != clockDevice.getClockMac() && !"".equals(clockDevice.getClockMac())) {
						filter.put("clockMac", clockDevice.getClockMac());
						List<ClockDevice> devices = deviceService.queryClockDevicesFilter(filter);
						if (null != devices && 0 < devices.size()) {
							cacheClock = devices.get(0);
						}
//						cacheClock = deviceUtil.getClockDeviceByIP(clockDevice.getClockMac());
					} else {
						filter.clear();
						filter.put("clockIpAddr", clockDevice.getClockIpAddr());
						List<ClockDevice> devices = deviceService.queryClockDevicesFilter(filter);
						if (null != devices && 0 < devices.size()) {
							cacheClock = devices.get(0);
						}
//						cacheClock = deviceUtil.getClockDeviceByIP(clockDevice.getClockIpAddr());
					}
					
					if (null == cacheClock) {
						clockDevice.setId(UUIDUtil.get32UUID());
						clockDevice.setUpdateUser("服务器");
						clockDevice.setCreateUser("服务器");
						clockDevice.setUpdateDate(new Date());
						clockDevice.setCreateDate(new Date());
						deviceService.insertClockDevice(clockDevice);
//						deviceUtil.addClockDevice(clockDevice);
					} else {
						try {
							if (clockDevice.getClockIpAddr().equals(cacheClock.getClockIpAddr())) {
								copyObject(clockDevice, cacheClock);
								cacheClock.setUpdateUser("服务器");
								if (1 == cacheClock.getClockStatus()) {
									cacheClock.setClockStatus(0);
								}
								System.out.println("协议---更新时钟状态代码---更新时钟状态是" + cacheClock.getClockStatus());
								cacheClock.setUpdateDate(new Date());
								deviceService.updateClockDevice(cacheClock);
							}
//							deviceUtil.updateClockDevice(cacheClock);
						} catch(Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
			} catch (Exception e) {
				
			} finally {
				if (null != responseSocket) {
					responseSocket.close();
				}
			}
			
		}
		
	} 
	
	
	public void copyObject(Object src, Object dst) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field[] fields = src.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			if ("serialVersionUID".equals(field.getName()) ) {
				continue;
			}
			field.setAccessible(true);
			Field dstF = dst.getClass().getDeclaredField(field.getName());
			if (null != field.get(src)) {
				dstF.setAccessible(true);
				dstF.set(dst, field.get(src));
			}
		}
		
	}
	
}
