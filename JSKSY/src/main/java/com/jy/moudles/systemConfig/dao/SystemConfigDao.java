package com.jy.moudles.systemConfig.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * SystemConfig数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@MyBatisDao
public interface SystemConfigDao {

	/**
	 * 新增SystemConfig对象
	 *
	 * @param SystemConfig
	 */
	public void insertSystemConfig(SystemConfig SystemConfig);
	
	/**
	 * 更新SystemConfig对象
	 *
	 * @param SystemConfig
	 */
	public void updateSystemConfig(SystemConfig SystemConfig);
	
	/**
	 * 根据ID获取SystemConfig对象
	 *
	 * @param id
	 */
	public SystemConfig getSystemConfigById(String id);
	
	/**
	 * 根据key获取SystemConfig对象
	 *
	 * @param key
	 */
	public String getSystemConfigByKey(@Param("key")String key);
	
	/**
	 * 根据过滤条件获取SystemConfig列表对象
	 *
	 * @param filter
	 */
	public List<SystemConfig> querySystemConfigsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除SystemConfig列表对象
	 *
	 * @param id
	 */
	public void deleteSystemConfigById(String id);
	
	/**
	 * 根据Id集合批量删除SystemConfig列表对象
	 *
	 * @param ids
	 */
	public void deleteSystemConfigs(List<String> ids);
	
	
	/**
	 * 更新SystemConfig对象
	 *
	 * @param SystemConfig
	 */
	public void updateSystemConfigByKey(SystemConfig SystemConfig);
	
}



