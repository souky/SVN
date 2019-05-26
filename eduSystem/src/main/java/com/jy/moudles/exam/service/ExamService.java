package com.jy.moudles.exam.service;

import com.jy.moudles.exam.entity.Exam;
import java.util.List;
import java.util.Map;

/** 
 * exam业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface ExamService {

	/**
	 * 新增exam对象
	 *
	 * @param exam
	 */
	public int insertExam(Exam exam);
	
	/**
	 * 更新exam对象
	 *
	 * @param exam
	 */
	public void updateExam(Exam exam);
	
	/**
	 * 根据ID获取exam对象
	 *
	 * @param id
	 */
	public Exam getExamById(String id);
	
	/**
	 * 根据过滤条件获取exam列表对象
	 *
	 * @param filter
	 */
	public List<Exam> queryExamsFilter(Map<String, Object> filter);

	/**
	 * 根据过滤条件获取exam列表对象
	 *
	 * @param filter
	 */
	public List<Exam> queryExamsFilterNew(Map<String, Object> filter);

	/**
	 * 根据过滤条件获取exam列表对象
	 *
	 * @param filter
	 */
	public List<Exam> getExamListForTab(Map<String, Object> filter);
	
	/**
	 * 根据过滤条件获取exam列表对象（优化）
	 *
	 * @param filter
	 */
	public List<Exam> getExamListForTabNew(Map<String, Object> filter);
	
	/**
	 * 根据Id删除exam列表对象
	 *
	 * @param id
	 */
	public void deleteExamById(String id);
	
	/**
	 * 根据Id集合批量删除exam列表对象
	 *
	 * @param ids
	 */
	public void deleteExams(List<String> ids);

	/**
	 * 根据orgId获取最近一次已完成的exam对象
	 *
	 * @param orgId
	 */
	public Exam getOrgLastCompletedExam(String orgId);

	public List<Exam> queryStuExamsFilter(Map<String, Object> filter);

}

