package com.jy.moudles.knowledgePoint.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.knowledgePoint.entity.KnowledgePoint;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * knowledgePoint数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface KnowledgePointDao {

	/**
	 * 新增knowledgePoint对象
	 *
	 * @param knowledgePoint
	 */
	public void insertKnowledgePoint(KnowledgePoint knowledgePoint);
	
	/**
	 * 更新knowledgePoint对象
	 *
	 * @param knowledgePoint
	 */
	public void updateKnowledgePoint(KnowledgePoint knowledgePoint);
	
	/**
	 * 根据ID获取knowledgePoint对象
	 *
	 * @param id
	 */
	public KnowledgePoint getKnowledgePointById(String id);
	
	/**
	 * 根据过滤条件获取knowledgePoint列表对象
	 *
	 * @param filter
	 */
	public List<KnowledgePoint> queryKnowledgePointsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除knowledgePoint列表对象
	 *
	 * @param id
	 */
	public void deleteKnowledgePointById(String id);
	
	/**
	 * 根据Id集合批量删除knowledgePoint列表对象
	 *
	 * @param ids
	 */
	public void deleteKnowledgePoints(List<String> ids);
	
}



