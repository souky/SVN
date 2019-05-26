package com.jy.moudles.systemConfig.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * systemConfig
 * wb
 */
@MyBatisDao
public interface SystemConfigDao {

	/**
	 * @param systemConfig
	 */
	public void insertSystemConfig(SystemConfig systemConfig);
	
	/**
	 * @param systemConfig
	 */
	public void updateSystemConfig(SystemConfig systemConfig);
	
	/**
	 * @param key
	 */
	public SystemConfig getSystemConfigByKey(String key);
	
	/**
	 * @param filter
	 */
	public List<SystemConfig> querySystemConfigsFilter(Map<String, Object> filter);
	
	/**
	 * @param key
	 */
	public void deleteSystemConfigByKey(String key);
	
	/**
	 * @param ids
	 */
	public void deleteSystemConfigs(List<String> ids);

	public void setSystemConfigByKey(@Param("sysKey")String key, 
			@Param("sysValue")String value);
	
}



