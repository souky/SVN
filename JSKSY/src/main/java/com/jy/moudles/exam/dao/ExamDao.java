package com.jy.moudles.exam.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.exam.entity.Exam;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * Exam数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@MyBatisDao
public interface ExamDao {

	/**
	 * 新增Exam对象
	 *
	 * @param Exam
	 */
	public void insertExam(Exam Exam);
	
	/**
	 * 更新Exam对象
	 *
	 * @param Exam
	 */
	public void updateExam(Exam Exam);
	
	/**
	 * 根据ID获取Exam对象
	 *
	 * @param id
	 */
	public Exam getExamByNo(String ksbh);
	
	/**
	 * 根据过滤条件获取Exam列表对象
	 *
	 * @param filter
	 */
	public List<Exam> queryExamsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除Exam列表对象
	 *
	 * @param id
	 */
	public void deleteExamByNo(String ksbh);
	
	/**
	 * 根据Id集合批量删除Exam列表对象
	 *
	 * @param ids
	 */
	public void deleteExams(List<String> ids);
	
	/**
	 * 获取全部exam对象 带考试场次
	 *
	 * @param id
	 */
	public List<Exam> queryAllExamWithSeason();
	
	
}



