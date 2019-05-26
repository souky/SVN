package com.jy.moudles.spDetailStep.service;

import com.jy.moudles.spDetailStep.entity.SpDetailStep;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;

import java.util.List;
import java.util.Map;

/** 
 * spDetailStep业务接口
 * 创建人：1
 * 创建时间：2018-01-08
 */
public interface SpDetailStepService {

	/**
	 * 新增spDetailStep对象
	 *
	 * @param spDetailStep
	 */
	public void insertSpDetailStep(SpDetailStep spDetailStep);
	
	/**
	 * 更新spDetailStep对象
	 *
	 * @param spDetailStep
	 */
	public void updateSpDetailStep(SpDetailStep spDetailStep);
	
	/**
	 * 根据ID获取spDetailStep对象
	 *
	 * @param id
	 */
	public SpDetailStep getSpDetailStepById(String id);
	
	/**
	 * 根据过滤条件获取spDetailStep列表对象
	 *
	 * @param filter
	 */
	public List<SpDetailStep> querySpDetailStepsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除spDetailStep列表对象
	 *
	 * @param id
	 */
	public void deleteSpDetailStepById(String id);
	
	/**
	 * 根据Id集合批量删除spDetailStep列表对象
	 *
	 * @param ids
	 */
	public void deleteSpDetailSteps(List<String> ids);
	
	public void insertSpDetailStepList(List<SpDetailStep> list,TwoWaySpecificationDetail e);
	
}

