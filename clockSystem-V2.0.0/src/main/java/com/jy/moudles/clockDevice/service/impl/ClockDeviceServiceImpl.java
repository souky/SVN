package com.jy.moudles.clockDevice.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.moudles.clockDevice.dao.ClockDeviceDao;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

/** 
 * clockDevice业务实现类
 * 创建人：1
 * 创建时间：2018-03-14
 */
@Service
public class ClockDeviceServiceImpl implements ClockDeviceService {

	@Autowired
	private ClockDeviceDao clockDeviceDao;
	
	@Override
	public void insertClockDevice(ClockDevice clockDevice){
		clockDeviceDao.insertClockDevice(clockDevice);
	}
	
	@Override
	public void updateClockDevice(ClockDevice clockDevice){
//		ClockDevice cd = clockDeviceDao.getClockDeviceById(clockDevice.getId());
//		if(null != cd) {
//			
//		}
		clockDeviceDao.updateClockDevice(clockDevice);
	}
	
	@Override
	public ClockDevice getClockDeviceById(String id){
		return clockDeviceDao.getClockDeviceById(id);
	}
	
	@Override
	public List<ClockDevice> queryClockDevicesFilter(Map<String, Object> filter){
		return clockDeviceDao.queryClockDevicesFilter(filter);
	}
	
	@Override
	public void deleteClockDeviceById(String id){
		clockDeviceDao.deleteClockDeviceById(id);
	}
	
	@Override
	public void deleteClockDevices(List<String> ids){
		clockDeviceDao.deleteClockDevices(ids);
	}
	
	@Override
	public List<ClockDevice> queryClockDevicesByOrderList() {
		List<ClockDevice> list = clockDeviceDao.queryClockDevicesByOrderList();
		//处理时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		for(ClockDevice e : list) {
			Date d = e.getClockTime();
			String time = sdf.format(d);
			e.setTime(time);
		}
		return list;
	}
	
	@Override
	public List<ClockDevice> getIdleClockDevices() {
		return clockDeviceDao.getIdleClockDevices();
	}
	
	@Override
	public void bootDevice(String id) {
		clockDeviceDao.bootDevice(id);
	}

	@Override
	public void bitchModifyStatus(List<String> ips) {
		clockDeviceDao.bitchModifyStatus(ips);
	}

	@Override
	public void bitchUpdateClock(List<ClockDevice> clocks) {
		clockDeviceDao.bitchUpdateClock(clocks);
	}
	
	@Override
	public void deleteClockDeviceAll() {
		clockDeviceDao.deleteClockDeviceAll();
	}
	
	@Override
	public void updateStatusBySource(String source) {
		clockDeviceDao.updateStatusBySource(source);
	}
}

