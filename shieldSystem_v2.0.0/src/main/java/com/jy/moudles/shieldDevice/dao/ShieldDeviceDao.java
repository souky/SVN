package com.jy.moudles.shieldDevice.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.shieldDevice.VO.CountVO;
import com.jy.moudles.shieldDevice.entity.ShieldDevice;

/** 
 * shieldDevice数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldDeviceDao {

	/**
	 * 新增shieldDevice对象
	 *
	 * @param shieldDevice
	 */
	public void insertShieldDevice(ShieldDevice shieldDevice);
	
	/**
	 * 更新shieldDevice对象
	 *
	 * @param shieldDevice
	 */
	public void updateShieldDevice(ShieldDevice shieldDevice);
	
	/**
	 * 根据ID获取shieldDevice对象
	 *
	 * @param id
	 */
	public ShieldDevice getShieldDeviceById(String id);
	
	/**
	 * 根据MAC获取shieldDevice对象
	 *
	 * @param id
	 */
	public ShieldDevice getShieldDeviceByMac(String mac);
	
	/**
	 * 根据过滤条件获取shieldDevice列表对象
	 *
	 * @param filter
	 */
	public List<ShieldDevice> queryShieldDevicesFilter(Map<String, Object> filter);
	
	/**
	 * 根据过滤条件获取shieldDevice列表对象 带地址信息
	 *
	 * @param filter
	 */
	public List<ShieldDevice> queryShieldDevicesFilterWithPosition(Map<String, Object> filter);
	
	/**
	 * 获取未绑定shieldDevice列表对象
	 *
	 * @param filter
	 */
	public List<ShieldDevice> getUnBindingShieldDevice(Map<String, Object> filter);
	
	/**
	 * 统计信息
	 *
	 * @param filter
	 */
	public List<CountVO> getShieldStatusCount();
	
	
	/**
	 * 根据Id删除shieldDevice列表对象
	 *
	 * @param id
	 */
	public void deleteShieldDeviceById(String id);
	
	/**
	 * 根据Id集合批量删除shieldDevice列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldDevices(List<String> ids);
	
}



