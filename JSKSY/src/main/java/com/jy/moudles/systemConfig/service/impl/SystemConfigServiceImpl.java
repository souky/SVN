package com.jy.moudles.systemConfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.systemConfig.dao.SystemConfigDao;
import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.moudles.systemConfig.service.SystemConfigService;

/** 
 * SystemConfig业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	@Autowired
	private SystemConfigDao SystemConfigDao;
	
	@Override
	public void insertSystemConfig(SystemConfig SystemConfig){
		SystemConfig.setId(UUIDUtil.get32UUID());
		SystemConfigDao.insertSystemConfig(SystemConfig);
	}
	
	@Override
	public void updateSystemConfig(SystemConfig SystemConfig){
		SystemConfigDao.updateSystemConfig(SystemConfig);
	}
	
	@Override
	public SystemConfig getSystemConfigById(String id){
		return SystemConfigDao.getSystemConfigById(id);
	}
	
	@Override
	public List<SystemConfig> querySystemConfigsFilter(Map<String, Object> filter){
		return SystemConfigDao.querySystemConfigsFilter(filter);
	}
	
	@Override
	public void deleteSystemConfigById(String id){
		SystemConfigDao.deleteSystemConfigById(id);
	}
	
	@Override
	public void deleteSystemConfigs(List<String> ids){
		SystemConfigDao.deleteSystemConfigs(ids);
	}

	@Override
	public String getSystemConfigByKey(String key) {
		return SystemConfigDao.getSystemConfigByKey(key);
	}

	@Override
	public void updateSystemConfigByKey(SystemConfig SystemConfig) {
		SystemConfigDao.updateSystemConfigByKey(SystemConfig);
	}
	
}

