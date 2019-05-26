package com.jy.moudles.shieldDevicePosition.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.shieldDevicePosition.entity.ShieldDevicePosition;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * shieldDevicePosition数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldDevicePositionDao {

	/**
	 * 新增shieldDevicePosition对象
	 *
	 * @param shieldDevicePosition
	 */
	public void insertShieldDevicePosition(ShieldDevicePosition shieldDevicePosition);
	
	/**
	 * 更新shieldDevicePosition对象
	 *
	 * @param shieldDevicePosition
	 */
	public void updateShieldDevicePosition(ShieldDevicePosition shieldDevicePosition);
	
	/**
	 * 根据ID获取shieldDevicePosition对象
	 *
	 * @param id
	 */
	public ShieldDevicePosition getShieldDevicePositionById(String id);
	
	/**
	 * 根据过滤条件获取shieldDevicePosition列表对象
	 *
	 * @param filter
	 */
	public List<ShieldDevicePosition> queryShieldDevicePositionsFilter(Map<String, Object> filter);
	
	
	/**
	 * 根据Id删除shieldDevicePosition列表对象
	 *
	 * @param id
	 */
	public void deleteShieldDevicePositionById(String id);
	
	/**
	 * 根据Id集合批量删除shieldDevicePosition列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldDevicePositions(List<String> ids);
	
}



