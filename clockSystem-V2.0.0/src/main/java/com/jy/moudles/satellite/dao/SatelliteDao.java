package com.jy.moudles.satellite.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.satellite.entity.Satellite;

/** 
 * Satellite数据接口
 * 创建人：1
 * 创建时间：2018-03-20
 */
@MyBatisDao
public interface SatelliteDao {

	/**
	 * 新增Satellite对象
	 *
	 * @param Satellite
	 */
	public void insertSatellite(Satellite Satellite);
	
	/**
	 * 更新Satellite对象
	 *
	 * @param Satellite
	 */
	public void updateSatellite(Satellite Satellite);
	
	/**
	 * 根据ID获取Satellite对象
	 *
	 * @param id
	 */
	public Satellite getSatelliteById(String id);
	
	/**
	 * 根据saNo获取Satellite对象
	 *
	 * @param id
	 */
	public Satellite getSatelliteBySaNo(String saNo);
	
	/**
	 * 根据过滤条件获取Satellite列表对象
	 *
	 * @param filter
	 */
	public List<Satellite> querySatellitesFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除Satellite列表对象
	 *
	 * @param id
	 */
	public void deleteSatelliteById(String id);
	
	/**
	 * 根据Id集合批量删除Satellite列表对象
	 *
	 * @param ids
	 */
	public void deleteSatellites(List<String> ids);
	
	/**
	 * 清空表
	 *
	 * @param ids
	 */
	public void deleteSatelliteAll();
	
	
	
}



