package com.jy.moudles.shieldLog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldLog.dao.ShieldLogDao;
import com.jy.moudles.shieldLog.entity.ShieldLog;
import com.jy.moudles.shieldLog.service.ShieldLogService;

/** 
 * shieldLog业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldLogServiceImpl implements ShieldLogService {

	@Autowired
	private ShieldLogDao shieldLogDao;
	
	@Override
	public void insertShieldLog(ShieldLog shieldLog){
		shieldLog.setId(UUIDUtil.get32UUID());
		shieldLogDao.insertShieldLog(shieldLog);
	}
	
	@Override
	public void updateShieldLog(ShieldLog shieldLog){
		shieldLogDao.updateShieldLog(shieldLog);
	}
	
	@Override
	public ShieldLog getShieldLogById(String id){
		return shieldLogDao.getShieldLogById(id);
	}
	
	@Override
	public List<ShieldLog> queryShieldLogsFilter(Map<String, Object> filter){
		return shieldLogDao.queryShieldLogsFilter(filter);
	}
	
	@Override
	public void deleteShieldLogById(String id){
		shieldLogDao.deleteShieldLogById(id);
	}
	
	@Override
	public void deleteShieldLogs(List<String> ids){
		shieldLogDao.deleteShieldLogs(ids);
	}
	
}

