package com.jy.moudles.clockDevicePosition.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockDevicePosition.dao.ClockDevicePositionDao;
import com.jy.moudles.clockDevicePosition.entity.ClockDevicePosition;
import com.jy.moudles.clockDevicePosition.service.ClockDevicePositionService;

/** 
 * clockDevicePosition业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockDevicePositionServiceImpl implements ClockDevicePositionService {

	@Autowired
	private ClockDevicePositionDao clockDevicePositionDao;
	
	@Override
	public void insertClockDevicePosition(ClockDevicePosition clockDevicePosition){
		clockDevicePosition.setId(UUIDUtil.get32UUID());
		clockDevicePositionDao.insertClockDevicePosition(clockDevicePosition);
	}
	
	@Override
	public void updateClockDevicePosition(ClockDevicePosition clockDevicePosition){
		clockDevicePositionDao.updateClockDevicePosition(clockDevicePosition);
	}
	
	@Override
	public ClockDevicePosition getClockDevicePositionById(String id){
		return clockDevicePositionDao.getClockDevicePositionById(id);
	}
	
	@Override
	public List<ClockDevicePosition> queryClockDevicePositionsFilter(Map<String, Object> filter){
		return clockDevicePositionDao.queryClockDevicePositionsFilter(filter);
	}
	
	@Override
	public void deleteClockDevicePositionById(String id){
		clockDevicePositionDao.deleteClockDevicePositionById(id);
	}
	
	@Override
	public void deleteClockDevicePositions(List<String> ids){
		clockDevicePositionDao.deleteClockDevicePositions(ids);
	}

	@Override
	public ClockDevicePosition getClockDevicePositionByClockId(String id) {
		return clockDevicePositionDao.getClockDevicePositionByClockId(id);
	}

	@Override
	public void deleteClockDevicePositionAll() {
		clockDevicePositionDao.deleteClockDevicePositionAll();
	}

	@Override
	public void unBinding(String id) {
		clockDevicePositionDao.unBinding(id);
	}

	@Override
	public void insertBatch(List<ClockDevicePosition> list) {
		clockDevicePositionDao.insertBatch(list);
	}
	
}

