package com.jy.moudles.clockSat.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.clockSat.entity.ClockSat;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * clockSat数据接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@MyBatisDao
public interface ClockSatDao {

	/**
	 * 新增clockSat对象
	 *
	 * @param clockSat
	 */
	public void insertClockSat(ClockSat clockSat);
	
	/**
	 * 更新clockSat对象
	 *
	 * @param clockSat
	 */
	public void updateClockSat(ClockSat clockSat);
	
	/**
	 * 根据ID获取clockSat对象
	 *
	 * @param id
	 */
	public ClockSat getClockSatById(String id);
	
	/**
	 * 根据过滤条件获取clockSat列表对象
	 *
	 * @param filter
	 */
	public List<ClockSat> queryClockSatsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除clockSat列表对象
	 *
	 * @param id
	 */
	public void deleteClockSatById(String id);
	
	/**
	 * 根据Id集合批量删除clockSat列表对象
	 *
	 * @param ids
	 */
	public void deleteClockSats(List<String> ids);
	
}



