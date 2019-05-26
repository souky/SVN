package com.jy.moudles.clockDevice.service;

import com.jy.moudles.clockDevice.VO.CountVO;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import java.util.List;
import java.util.Map;

/** 
 * ClockDevice业务接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
public interface ClockDeviceService {

	/**
	 * 新增ClockDevice对象
	 *
	 * @param ClockDevice
	 */
	public void insertClockDevice(ClockDevice clockDevice);
	
	/**
	 * 更新ClockDevice对象
	 *
	 * @param ClockDevice
	 */
	public void updateClockDevice(ClockDevice clockDevice);
	
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

