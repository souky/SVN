package com.jy.moudles.shieldDevicePosition.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldDevicePosition.dao.ShieldDevicePositionDao;
import com.jy.moudles.shieldDevicePosition.entity.ShieldDevicePosition;
import com.jy.moudles.shieldDevicePosition.service.ShieldDevicePositionService;

/** 
 * shieldDevicePosition业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldDevicePositionServiceImpl implements ShieldDevicePositionService {

	@Autowired
	private ShieldDevicePositionDao shieldDevicePositionDao;
	
	@Override
	public void insertShieldDevicePosition(ShieldDevicePosition shieldDevicePosition){
		shieldDevicePosition.setId(UUIDUtil.get32UUID());
		shieldDevicePositionDao.insertShieldDevicePosition(shieldDevicePosition);
	}
	
	@Override
	public void updateShieldDevicePosition(ShieldDevicePosition shieldDevicePosition){
		shieldDevicePositionDao.updateShieldDevicePosition(shieldDevicePosition);
	}
	
	@Override
	public ShieldDevicePosition getShieldDevicePositionById(String id){
		return shieldDevicePositionDao.getShieldDevicePositionById(id);
	}
	
	@Override
	public List<ShieldDevicePosition> queryShieldDevicePositionsFilter(Map<String, Object> filter){
		return shieldDevicePositionDao.queryShieldDevicePositionsFilter(filter);
	}
	
	@Override
	public void deleteShieldDevicePositionById(String id){
		shieldDevicePositionDao.deleteShieldDevicePositionById(id);
	}
	
	@Override
	public void deleteShieldDevicePositions(List<String> ids){
		shieldDevicePositionDao.deleteShieldDevicePositions(ids);
	}
	
}

