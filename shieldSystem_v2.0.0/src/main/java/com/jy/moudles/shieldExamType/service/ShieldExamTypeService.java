package com.jy.moudles.shieldExamType.service;

import com.jy.moudles.shieldExamType.entity.ShieldExamType;
import java.util.List;
import java.util.Map;

/** 
 * shieldExamType业务接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
public interface ShieldExamTypeService {

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

