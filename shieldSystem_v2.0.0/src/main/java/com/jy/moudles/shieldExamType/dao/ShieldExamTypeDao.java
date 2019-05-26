package com.jy.moudles.shieldExamType.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.shieldExamType.entity.ShieldExamType;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * shieldExamType数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldExamTypeDao {

	/**
	 * 新增shieldExamType对象
	 *
	 * @param shieldExamType
	 */
	public void insertShieldExamType(ShieldExamType shieldExamType);
	
	/**
	 * 更新shieldExamType对象
	 *
	 * @param shieldExamType
	 */
	public void updateShieldExamType(ShieldExamType shieldExamType);
	
	/**
	 * 根据ID获取shieldExamType对象
	 *
	 * @param id
	 */
	public ShieldExamType getShieldExamTypeById(String id);
	
	/**
	 * 根据过滤条件获取shieldExamType列表对象
	 *
	 * @param filter
	 */
	public List<ShieldExamType> queryShieldExamTypesFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除shieldExamType列表对象
	 *
	 * @param id
	 */
	public void deleteShieldExamTypeById(String id);
	
	/**
	 * 根据Id集合批量删除shieldExamType列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldExamTypes(List<String> ids);
	
}



