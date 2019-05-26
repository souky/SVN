package com.jy.moudles.clockDevice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockDevice.VO.CountVO;
import com.jy.moudles.clockDevice.dao.ClockDeviceDao;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

/** 
 * ClockDevice业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockDeviceServiceImpl implements ClockDeviceService {

	@Autowired
	private ClockDeviceDao ClockDeviceDao;
	
	@Override
	public void insertClockDevice(ClockDevice ClockDevice){
		ClockDevice.setId(UUIDUtil.get32UUID());
		ClockDeviceDao.insertClockDevice(ClockDevice);
	}
	
	@Override
	public void updateClockDevice(ClockDevice ClockDevice){
		ClockDeviceDao.updateClockDevice(ClockDevice);
	}
	
	@Override
	public ClockDevice getClockDeviceById(String id){
		return ClockDeviceDao.getClockDeviceById(id);
	}
	
	@Override
	public List<ClockDevice> queryClockDevicesFilter(Map<String, Object> filter){
		return ClockDeviceDao.queryClockDevicesFilter(filter);
	}
	
	@Override
	public List<ClockDevice> queryClockDevicesFilterWithPosition(Map<String, Object> filter){
		return ClockDeviceDao.queryClockDevicesFilterWithPosition(filter);
	}
	
	@Override
	public void deleteClockDeviceById(String id){
		ClockDeviceDao.deleteClockDeviceById(id);
	}
	
	@Override
	public void deleteClockDevices(List<String> ids){
		ClockDeviceDao.deleteClockDevices(ids);
	}

	@Override
	public List<ClockDevice> getUnBindingClockDevice(Map<String, Object> filter) {
		return ClockDeviceDao.getUnBindingClockDevice(filter);
	}

	@Override
	public List<CountVO> getClockStatusCount() {
		return ClockDeviceDao.getClockStatusCount();
	}
	
}

