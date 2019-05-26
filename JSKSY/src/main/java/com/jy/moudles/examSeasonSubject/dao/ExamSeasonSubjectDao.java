package com.jy.moudles.examSeasonSubject.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.examSeasonSubject.entity.ExamSeasonSubject;

/** 
 * ExamSeasonSubject数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@MyBatisDao
public interface ExamSeasonSubjectDao {

	/**
	 * 新增ExamSeasonSubject对象
	 *
	 * @param ExamSeasonSubject
	 */
	public void insertExamSeasonSubject(ExamSeasonSubject ExamSeasonSubject);
	
	/**
	 * 更新ExamSeasonSubject对象
	 *
	 * @param ExamSeasonSubject
	 */
	public void updateExamSeasonSubject(ExamSeasonSubject ExamSeasonSubject);
	
	/**
	 * 根据ID获取ExamSeasonSubject对象
	 *
	 * @param id
	 */
	public ExamSeasonSubject getExamSeasonSubjectById(String id);
	
	/**
	 * 根据过滤条件获取ExamSeasonSubject列表对象
	 *
	 * @param filter
	 */
	public List<ExamSeasonSubject> queryExamSeasonSubjectsFilter(Map<String, Object> filter);
	
	/**
	 * 根据seasonId删除ExamSeasonSubject列表对象
	 *
	 * @param id
	 */
	public void deleteExamSeasonSubjectBySeasonNo(String examSeasonNo);
	
	/**
	 * 根据Id集合批量删除ExamSeasonSubject列表对象
	 *
	 * @param ids
	 */
	public void deleteExamSeasonSubjects(List<String> ids);
	
	/**
	 * 批量添加ExamSeasonSubject列表对象
	 *
	 * @param ExamSeasonSubject
	 */
	public void insertExamSeasonSubjects(List<ExamSeasonSubject> examSeasonSubjects);
	
	
	
	
}



