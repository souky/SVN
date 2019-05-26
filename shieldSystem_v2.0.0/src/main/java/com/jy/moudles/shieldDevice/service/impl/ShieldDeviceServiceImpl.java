package com.jy.moudles.shieldDevice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldDevice.VO.CountVO;
import com.jy.moudles.shieldDevice.dao.ShieldDeviceDao;
import com.jy.moudles.shieldDevice.entity.ShieldDevice;
import com.jy.moudles.shieldDevice.service.ShieldDeviceService;

/** 
 * shieldDevice业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldDeviceServiceImpl implements ShieldDeviceService {

	@Autowired
	private ShieldDeviceDao shieldDeviceDao;
	
	@Override
	public void insertShieldDevice(ShieldDevice shieldDevice){
		shieldDevice.setId(UUIDUtil.get32UUID());
		shieldDeviceDao.insertShieldDevice(shieldDevice);
	}
	
	@Override
	public void updateShieldDevice(ShieldDevice shieldDevice){
		shieldDeviceDao.updateShieldDevice(shieldDevice);
	}
	
	@Override
	public ShieldDevice getShieldDeviceById(String id){
		return shieldDeviceDao.getShieldDeviceById(id);
	}
	
	@Override
	public List<ShieldDevice> queryShieldDevicesFilter(Map<String, Object> filter){
		return shieldDeviceDao.queryShieldDevicesFilter(filter);
	}
	
	@Override
	public void deleteShieldDeviceById(String id){
		shieldDeviceDao.deleteShieldDeviceById(id);
	}
	
	@Override
	public void deleteShieldDevices(List<String> ids){
		shieldDeviceDao.deleteShieldDevices(ids);
	}

	@Override
	public ShieldDevice getShieldDeviceByMac(String mac) {
		return shieldDeviceDao.getShieldDeviceByMac(mac);
	}

	@Override
	public List<ShieldDevice> queryShieldDevicesFilterWithPosition(Map<String, Object> filter) {
		return shieldDeviceDao.queryShieldDevicesFilterWithPosition(filter);
	}

	@Override
	public List<ShieldDevice> getUnBindingShieldDevice(Map<String, Object> filter) {
		return shieldDeviceDao.getUnBindingShieldDevice(filter);
	}

	@Override
	public List<CountVO> getShieldStatusCount() {
		return shieldDeviceDao.getShieldStatusCount();
	}
	
}

