package com.jy.moudles.spDetailKnowledgeRelation.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.spDetailKnowledgeRelation.entity.SpDetailKnowledgeRelation;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * spDetailKnowledgeRelation数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface SpDetailKnowledgeRelationDao {

	/**
	 * 新增spDetailKnowledgeRelation对象
	 *
	 * @param spDetailKnowledgeRelation
	 */
	public void insertSpDetailKnowledgeRelation(SpDetailKnowledgeRelation spDetailKnowledgeRelation);
	
	/**
	 * 更新spDetailKnowledgeRelation对象
	 *
	 * @param spDetailKnowledgeRelation
	 */
	public void updateSpDetailKnowledgeRelation(SpDetailKnowledgeRelation spDetailKnowledgeRelation);
	
	/**
	 * 根据ID获取spDetailKnowledgeRelation对象
	 *
	 * @param id
	 */
	public SpDetailKnowledgeRelation getSpDetailKnowledgeRelationById(String id);
	
	/**
	 * 根据过滤条件获取spDetailKnowledgeRelation列表对象
	 *
	 * @param filter
	 */
	public List<SpDetailKnowledgeRelation> querySpDetailKnowledgeRelationsFilter(Map<String, Object> filter);
	
	/**
	 * 根据spDetailId删除spDetailKnowledgeRelation列表对象
	 *
	 * @param spDetailId
	 */
	public void deleteSDKRelationBySpDetailId(String spDetailId);
	
	/**
	 * 根据Id集合批量删除spDetailKnowledgeRelation列表对象
	 *
	 * @param ids
	 */
	public void deleteSpDetailKnowledgeRelations(List<String> ids);
	
}



