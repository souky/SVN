package com.jy.moudles.examStudentRelation.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.examStudentRelation.entity.ExamStudentRelation;
import com.jy.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/** 
 * examStudentRelation数据接口
 * 创建人：1
 * 创建时间：2017-11-30
 */
@MyBatisDao
public interface ExamStudentRelationDao {

	/**
	 * 新增examStudentRelation对象
	 *
	 * @param examStudentRelation
	 */
	public void insertExamStudentRelation(ExamStudentRelation examStudentRelation);

	/**
	 * 批量新增examStudentRelation对象
	 *
	 * @param examStudentRelations
	 */
	public void batchInsertExamStudentRelation(@Param("examStudentRelations") List<ExamStudentRelation> examStudentRelations);
	
	/**
	 * 更新examStudentRelation对象
	 *
	 * @param examStudentRelation
	 */
	public void updateExamStudentRelation(ExamStudentRelation examStudentRelation);
	
	/**
	 * 根据ID获取examStudentRelation对象
	 *
	 * @param id
	 */
	public ExamStudentRelation getExamStudentRelationById(String id);
	
	/**
	 * 根据过滤条件获取examStudentRelation列表对象
	 *
	 * @param filter
	 */
	public List<ExamStudentRelation> queryExamStudentRelationsFilter(Map<String, Object> filter);
	
	/**
	 * 根据examId删除examStudentRelation列表对象
	 *
	 * @param examId
	 */
	public void deleteExamStudentRelationByExamId(String examId);
	
	/**
	 * 根据Id集合批量删除examStudentRelation列表对象
	 *
	 * @param ids
	 */
	public void deleteExamStudentRelations(List<String> ids);
	
}



