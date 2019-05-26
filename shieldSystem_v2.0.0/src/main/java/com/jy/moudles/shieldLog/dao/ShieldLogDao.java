package com.jy.moudles.shieldLog.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.shieldLog.entity.ShieldLog;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * shieldLog数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldLogDao {

	/**
	 * 新增shieldLog对象
	 *
	 * @param shieldLog
	 */
	public void insertShieldLog(ShieldLog shieldLog);
	
	/**
	 * 更新shieldLog对象
	 *
	 * @param shieldLog
	 */
	public void updateShieldLog(ShieldLog shieldLog);
	
	/**
	 * 根据ID获取shieldLog对象
	 *
	 * @param id
	 */
	public ShieldLog getShieldLogById(String id);
	
	/**
	 * 根据过滤条件获取shieldLog列表对象
	 *
	 * @param filter
	 */
	public List<ShieldLog> queryShieldLogsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除shieldLog列表对象
	 *
	 * @param id
	 */
	public void deleteShieldLogById(String id);
	
	/**
	 * 根据Id集合批量删除shieldLog列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldLogs(List<String> ids);
	
}



