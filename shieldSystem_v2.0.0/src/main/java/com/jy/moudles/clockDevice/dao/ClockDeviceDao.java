package com.jy.moudles.clockDevice.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.clockDevice.VO.CountVO;
import com.jy.moudles.clockDevice.entity.ClockDevice;

/** 
 * ClockDevice数据接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@MyBatisDao
public interface ClockDeviceDao {

	/**
	 * 新增ClockDevice对象
	 *
	 * @param ClockDevice
	 */
	public void insertClockDevice(ClockDevice ClockDevice);
	
	/**
	 * 更新ClockDevice对象
	 *
	 * @param ClockDevice
	 */
	public void updateClockDevice(ClockDevice ClockDevice);
	
	/**
	 * 根据ID获取ClockDevice对象
	 *
	 * @param id
	 */
	public ClockDevice getClockDeviceById(String id);
	
	/**
	 * 根据过滤条件获取ClockDevice列表对象
	 *
	 * @param filter
	 */
	public List<ClockDevice> queryClockDevicesFilter(Map<String, Object> filter);
	
	/**
	 * 根据过滤条件获取ClockDevice列表对象(带位置信息)
	 *
	 * @param filter
	 */
	public List<ClockDevice> queryClockDevicesFilterWithPosition(Map<String, Object> filter);
	
	/**
	 * 根据过滤条件获取未绑定ClockDevice列表对象
	 *
	 * @param filter
	 */
	public List<ClockDevice> getUnBindingClockDevice(Map<String, Object> filter);
	
	
	/**
	 * 统计信息
	 *
	 * @param filter
	 */
	public List<CountVO> getClockStatusCount();
	
	
	/**
	 * 根据Id删除ClockDevice列表对象
	 *
	 * @param id
	 */
	public void deleteClockDeviceById(String id);
	
	/**
	 * 根据Id集合批量删除ClockDevice列表对象
	 *
	 * @param ids
	 */
	public void deleteClockDevices(List<String> ids);
	
}



