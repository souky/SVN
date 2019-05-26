package com.jy.moudles.examSpecificationRelation.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.examSpecificationRelation.entity.ExamSpecificationRelation;
import com.jy.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/** 
 * examSpecificationRelation数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface ExamSpecificationRelationDao {

	/**
	 * 新增examSpecificationRelation对象
	 *
	 * @param examSpecificationRelation
	 */
	public void insertExamSpecificationRelation(ExamSpecificationRelation examSpecificationRelation);
	
	/**
	 * 批量新增examSpecificationRelation对象
	 *
	 * @param examSpecificationRelations
	 */
	public void batchInsertExamSpecificationRelation(@Param("examSpecificationRelations") List<ExamSpecificationRelation> examSpecificationRelations);

	/**
	 * 更新examSpecificationRelation对象
	 *
	 * @param examSpecificationRelation
	 */
	public void updateExamSpecificationRelation(ExamSpecificationRelation examSpecificationRelation);
	
	/**
	 * 根据ID获取examSpecificationRelation对象
	 *
	 * @param id
	 */
	public ExamSpecificationRelation getExamSpecificationRelationById(String id);
	
	/**
	 * 根据过滤条件获取examSpecificationRelation列表对象
	 *
	 * @param filter
	 */
	public List<ExamSpecificationRelation> queryExamSpecificationRelationsFilter(Map<String, Object> filter);
	
	/**
	 * 根据examId删除examSpecificationRelation列表对象
	 *
	 * @param examId
	 */
	public void deleteExamSpecificationRelationByExamId(String examId);
	
	/**
	 * 根据Id集合批量删除examSpecificationRelation列表对象
	 *
	 * @param ids
	 */
	public void deleteExamSpecificationRelations(List<String> ids);
	
}



