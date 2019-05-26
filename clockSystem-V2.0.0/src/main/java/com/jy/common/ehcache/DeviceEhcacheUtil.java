package com.jy.common.ehcache;

import java.util.ArrayList;
import java.util.List;

import com.jy.moudles.clockDevice.entity.ClockDevice;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class DeviceEhcacheUtil {
	private static DeviceEhcacheUtil deviceEhcacheUtil;
	
	private Cache deviceCache;
	
	public DeviceEhcacheUtil() {
		deviceCache = EhcacheUtil.getInstance().getCache("deviceCache");
	}
	
	public DeviceEhcacheUtil(CacheManager cacheManager) {
		deviceCache = cacheManager.getCache("deviceCache");
	}

	public static DeviceEhcacheUtil getInstance() {
		if (null == deviceEhcacheUtil) {
			deviceEhcacheUtil = new DeviceEhcacheUtil();
		}

		return deviceEhcacheUtil;
	}
	
	public void addClockDevice(ClockDevice clockDevice) {
		addClockDeviceByIP(clockDevice.getClockIpAddr(), clockDevice);
		addClockDeviceByID(clockDevice.getId(), clockDevice);
		addClockDeviceByMac(clockDevice.getClockMac(), clockDevice);
	}
	
	public void addClockDeviceByIP(String ip, ClockDevice clockDevice) {
		Element e = new Element(ip, clockDevice);
		deviceCache.put(e);
		addClockDeviceByID(clockDevice.getId(), clockDevice);
		addClockDeviceByMac(clockDevice.getClockMac(), clockDevice);
	}
	
	public void addClockDeviceByID(String id, ClockDevice clockDevice) {
		Element e = new Element(id, clockDevice);
		deviceCache.put(e);
		addClockDeviceByIP(clockDevice.getClockIpAddr(), clockDevice);
		addClockDeviceByMac(clockDevice.getClockMac(), clockDevice);
	}
	
	public void addClockDeviceByMac(String mac, ClockDevice clockDevice) {
		Element e = new Element(mac, clockDevice);
		deviceCache.put(e);
		addClockDeviceByID(clockDevice.getId(), clockDevice);
		addClockDeviceByIP(clockDevice.getClockIpAddr(), clockDevice);
	}
	
	public ClockDevice getClockDeviceByMac(String mac) {
		Element e = deviceCache.get(mac);
		return null == e ? null : (ClockDevice)e.getObjectValue();
	}
	
	public ClockDevice getClockDeviceByIP(String ip) {
		Element e = deviceCache.get(ip);
		return null == e ? null : (ClockDevice)e.getObjectValue();
	}
	
	public ClockDevice getClockDeviceById(String id) {
		Element e = deviceCache.get(id);
		return null == e ? null : (ClockDevice)e.getObjectValue();
	}
	
	public void updateClockDevice(ClockDevice clockDevice) {
		addClockDeviceByID(clockDevice.getId(), clockDevice);
		addClockDeviceByIP(clockDevice.getClockIpAddr(), clockDevice);
		addClockDeviceByMac(clockDevice.getClockMac(), clockDevice);
	}
	
	public void deleteClockDeviceByID(String id) {
		ClockDevice clockDevice = getClockDeviceById(id);
		if (null != clockDevice) {
			deleteClockDeviceByMac(clockDevice.getClockMac());
			deleteClockDeviceByIP(clockDevice.getClockIpAddr());
		}
		
		deviceCache.remove(id);
	}
	
	public void deleteClockDeviceByMac(String mac) {
		ClockDevice clockDevice = getClockDeviceByMac(mac);
		if (null != clockDevice) {
			deleteClockDeviceByID(clockDevice.getId());
			deleteClockDeviceByIP(clockDevice.getClockIpAddr());
		}
		deviceCache.remove(mac);
	}
	
	public void deleteClockDeviceByIP(String ip) {
		ClockDevice clockDevice = getClockDeviceByIP(ip);
		if (null != clockDevice) {
			deleteClockDeviceByMac(clockDevice.getClockMac());
			deleteClockDeviceByID(clockDevice.getId());
		}
		deviceCache.remove(ip);
	}
	
	public void deleteClockDeviceByIDs(List<String> ids) {
		for (String id : ids) {
			deleteClockDeviceByID(id);
		}
	}
	
	public void deleteClockDeviceByMacs(List<String> macs) {
		for (String mac : macs) {
			deleteClockDeviceByMac(mac);
		}
	}
	
	public void deleteClockDeviceByIPs(List<String> ips) {
		for (String ip : ips) {
			deleteClockDeviceByIP(ip);
		}
	}
	
	public List<ClockDevice> getAllClockDevice() {
		List<ClockDevice> devices = new ArrayList<ClockDevice>();
		for (Object key : deviceCache.getKeys()) {
			if (String.valueOf(key).contains(".")) {
				ClockDevice clockDevice = getClockDeviceByIP(String.valueOf(key));
				if (null != clockDevice) {
					devices.add(clockDevice);
				}
			}
		}
		return devices;
	}
}
