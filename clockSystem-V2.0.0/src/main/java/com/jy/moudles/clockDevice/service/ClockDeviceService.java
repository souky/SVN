package com.jy.moudles.clockDevice.service;

import com.jy.moudles.clockDevice.entity.ClockDevice;
import java.util.List;
import java.util.Map;

/** 
 * clockDevice业务接口
 * 创建人：1
 * 创建时间：2018-03-14
 */
public interface ClockDeviceService {

	/**
	 * 新增clockDevice对象
	 *
	 * @param clockDevice
	 */
	public void insertClockDevice(ClockDevice clockDevice);
	
	/**
	 * 更新clockDevice对象
	 *
	 * @param clockDevice
	 */
	public void updateClockDevice(ClockDevice clockDevice);
	
	/**
	 * 更新状态by source
	 * 
	 * @param ips
	 */
	public void updateStatusBySource(String source);
	
	/**
	 * 根据ID获取clockDevice对象
	 *
	 * @param id
	 */
	public ClockDevice getClockDeviceById(String id);
	
	/**
	 * 根据过滤条件获取clockDevice列表对象
	 *
	 * @param filter
	 */
	public List<ClockDevice> queryClockDevicesFilter(Map<String, Object> filter);
	
	/**
	 * 根据orderList获取clockDevice列表对象
	 *
	 */
	public List<ClockDevice> queryClockDevicesByOrderList();
	
	/**
	 * 查询未绑定的clockDevices
	 *
	 */
	public List<ClockDevice> getIdleClockDevices();
	
	/**
	 * 根据Id删除clockDevice列表对象
	 *
	 * @param id
	 */
	public void deleteClockDeviceById(String id);
	
	/**
	 * 根据Id集合批量删除clockDevice列表对象
	 *
	 * @param ids
	 */
	public void deleteClockDevices(List<String> ids);
	
	/**
	 * 删除clockDevice列表对象
	 *
	 * @param ids
	 */
	public void deleteClockDeviceAll();
	
	/**
	 * 解绑clockDevice
	 *
	 * @param id
	 */
	public void bootDevice(String id);
	
	/**
	 * 更新状态
	 * 
	 * @param ips
	 */
	public void bitchModifyStatus(List<String> ips);
	
	/**
	 * 更新时钟设备
	 * 
	 * @param clocks
	 */
	public void bitchUpdateClock(List<ClockDevice> clocks);
	
}

