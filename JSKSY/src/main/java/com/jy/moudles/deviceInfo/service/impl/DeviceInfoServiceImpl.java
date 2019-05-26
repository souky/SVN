package com.jy.moudles.deviceInfo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.deviceInfo.dao.DeviceInfoDao;
import com.jy.moudles.deviceInfo.entity.DeviceInfo;
import com.jy.moudles.deviceInfo.service.DeviceInfoService;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {

	@Autowired
	private DeviceInfoDao DeviceInfoDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return DeviceInfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DeviceInfo record) {
		record.setId(UUIDUtil.get32UUID());
		return DeviceInfoDao.insert(record);
	}

	@Override
	public DeviceInfo selectByPrimaryKey(String id) {
		return DeviceInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public List<DeviceInfo> selectAll() {
		return DeviceInfoDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(DeviceInfo record) {
		return DeviceInfoDao.updateByPrimaryKey(record);
	}
	
	
	@Override
	public List<DeviceInfo> queryDeviceInfoFilter(Map<String, Object> filter){
		return DeviceInfoDao.queryDeviceInfoFilter(filter);
	}
}

