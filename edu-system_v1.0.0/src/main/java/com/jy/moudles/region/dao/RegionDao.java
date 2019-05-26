package com.jy.moudles.region.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.region.entity.Region;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * region数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface RegionDao {

	/**
	 * 根据regionCode获取region对象
	 *
	 * @param regionCode
	 */
	public Region getRegionByCode(String regionCode);

	/**
	 * 根据ID获取region对象
	 *
	 * @param id
	 */
	public Region getRegionById(String id);

	/**
	 * 根据过滤条件获取region列表对象
	 *
	 * @param filter
	 */
	public List<Region> queryRegionsFilter(Map<String, Object> filter);
	
}



