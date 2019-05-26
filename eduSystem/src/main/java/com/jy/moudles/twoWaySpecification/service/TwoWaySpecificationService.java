package com.jy.moudles.twoWaySpecification.service;

import com.jy.moudles.twoWaySpecification.entity.TwoWaySpecification;
import java.util.List;
import java.util.Map;

/** 
 * twoWaySpecification业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface TwoWaySpecificationService {

	/**
	 * 新增twoWaySpecification对象
	 *
	 * @param twoWaySpecification
	 */
	public void insertTwoWaySpecification(TwoWaySpecification twoWaySpecification);
	
	/**
	 * 更新twoWaySpecification对象
	 *
	 * @param twoWaySpecification
	 */
	public void updateTwoWaySpecification(TwoWaySpecification twoWaySpecification);
	
	/**
	 * 根据ID获取twoWaySpecification对象
	 *
	 * @param id
	 */
	public TwoWaySpecification getTwoWaySpecificationById(String id);
	
	/**
	 * 根据过滤条件获取twoWaySpecification列表对象
	 *
	 * @param filter
	 */
	public List<TwoWaySpecification> queryTwoWaySpecificationsFilter(Map<String, Object> filter);
	
	public List<TwoWaySpecification> queryTwoWaySpecificationsFilterByCode(Map<String, Object> filter);

	public List<TwoWaySpecification> queryTotalTwoWaySpecificationsFilter(Map<String, Object> filter);
	
	public List<TwoWaySpecification> queryTwoWaySpecificationsByExamIdAndSubject(Map<String, Object> filter);
	
	/**
	 * 根据Id删除twoWaySpecification列表对象
	 *
	 * @param id
	 */
	public void deleteTwoWaySpecificationById(String id);
	
	/**
	 * 根据Id集合批量删除twoWaySpecification列表对象
	 *
	 * @param ids
	 */
	public void deleteTwoWaySpecifications(List<String> ids);

}

