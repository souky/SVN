package com.jy.moudles.clockSat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockSat.dao.ClockSatDao;
import com.jy.moudles.clockSat.entity.ClockSat;
import com.jy.moudles.clockSat.service.ClockSatService;

/** 
 * clockSat业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockSatServiceImpl implements ClockSatService {

	@Autowired
	private ClockSatDao clockSatDao;
	
	@Override
	public void insertClockSat(ClockSat clockSat){
		clockSat.setId(UUIDUtil.get32UUID());
		clockSatDao.insertClockSat(clockSat);
	}
	
	@Override
	public void updateClockSat(ClockSat clockSat){
		clockSatDao.updateClockSat(clockSat);
	}
	
	@Override
	public ClockSat getClockSatById(String id){
		return clockSatDao.getClockSatById(id);
	}
	
	@Override
	public List<ClockSat> queryClockSatsFilter(Map<String, Object> filter){
		return clockSatDao.queryClockSatsFilter(filter);
	}
	
	@Override
	public void deleteClockSatById(String id){
		clockSatDao.deleteClockSatById(id);
	}
	
	@Override
	public void deleteClockSats(List<String> ids){
		clockSatDao.deleteClockSats(ids);
	}
	
}

