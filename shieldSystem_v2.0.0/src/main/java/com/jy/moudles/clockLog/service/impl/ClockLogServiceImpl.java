package com.jy.moudles.clockLog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockLog.dao.ClockLogDao;
import com.jy.moudles.clockLog.entity.ClockLog;
import com.jy.moudles.clockLog.service.ClockLogService;

/** 
 * clockLog业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockLogServiceImpl implements ClockLogService {

	@Autowired
	private ClockLogDao clockLogDao;
	
	@Override
	public void insertClockLog(ClockLog clockLog){
		clockLog.setId(UUIDUtil.get32UUID());
		clockLogDao.insertClockLog(clockLog);
	}
	
	@Override
	public void updateClockLog(ClockLog clockLog){
		clockLogDao.updateClockLog(clockLog);
	}
	
	@Override
	public ClockLog getClockLogById(String id){
		return clockLogDao.getClockLogById(id);
	}
	
	@Override
	public List<ClockLog> queryClockLogsFilter(Map<String, Object> filter){
		return clockLogDao.queryClockLogsFilter(filter);
	}
	
	@Override
	public void deleteClockLogById(String id){
		clockLogDao.deleteClockLogById(id);
	}
	
	@Override
	public void deleteClockLogs(List<String> ids){
		clockLogDao.deleteClockLogs(ids);
	}
	
}

