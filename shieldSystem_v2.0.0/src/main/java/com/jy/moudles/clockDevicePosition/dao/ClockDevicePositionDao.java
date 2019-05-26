package com.jy.moudles.clockDevicePosition.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.clockDevicePosition.entity.ClockDevicePosition;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * clockDevicePosition数据接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@MyBatisDao
public interface ClockDevicePositionDao {

	/**
	 * 新增clockDevicePosition对象
	 *
	 * @param clockDevicePosition
	 */
	public void insertClockDevicePosition(ClockDevicePosition clockDevicePosition);
	
	/**
	 * 批量新增clockDevicePosition对象
	 *
	 * @param clockDevicePosition
	 */
	public void insertBatch(List<ClockDevicePosition> list);
	
	/**
	 * 更新clockDevicePosition对象
	 *
	 * @param clockDevicePosition
	 */
	public void updateClockDevicePosition(ClockDevicePosition clockDevicePosition);
	
	/**
	 * 根据ID获取clockDevicePosition对象
	 *
	 * @param id
	 */
	public ClockDevicePosition getClockDevicePositionById(String id);
	
	/**
	 * 根据clock_ID获取clockDevicePosition对象
	 *
	 * @param id
	 */
	public ClockDevicePosition getClockDevicePositionByClockId(String id);
	
	/**
	 * 根据过滤条件获取clockDevicePosition列表对象
	 *
	 * @param filter
	 */
	public List<ClockDevicePosition> queryClockDevicePositionsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除clockDevicePosition列表对象
	 *
	 * @param id
	 */
	public void deleteClockDevicePositionById(String id);
	
	/**
	 * 解绑时钟
	 *
	 * @param id
	 */
	public void unBinding(String id);
	
	/**
	 * 清空数据
	 */
	public void deleteClockDevicePositionAll();
	
	/**
	 * 根据Id集合批量删除clockDevicePosition列表对象
	 *
	 * @param ids
	 */
	public void deleteClockDevicePositions(List<String> ids);
	
}



