package com.jy.moudles.shieldExamPlan.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.shieldExamPlan.entity.ShieldExamPlan;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * shieldExamPlan数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldExamPlanDao {

	/**
	 * 新增shieldExamPlan对象
	 *
	 * @param shieldExamPlan
	 */
	public void insertShieldExamPlan(ShieldExamPlan shieldExamPlan);
	
	/**
	 * 更新shieldExamPlan对象
	 *
	 * @param shieldExamPlan
	 */
	public void updateShieldExamPlan(ShieldExamPlan shieldExamPlan);
	
	/**
	 * 根据ID获取shieldExamPlan对象
	 *
	 * @param id
	 */
	public ShieldExamPlan getShieldExamPlanById(String id);
	
	/**
	 * 根据过滤条件获取shieldExamPlan列表对象
	 *
	 * @param filter
	 */
	public List<ShieldExamPlan> queryShieldExamPlansFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除shieldExamPlan列表对象
	 *
	 * @param id
	 */
	public void deleteShieldExamPlanById(String id);
	
	/**
	 * 根据Id集合批量删除shieldExamPlan列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldExamPlans(List<String> ids);
	
}



