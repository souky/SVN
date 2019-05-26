package com.jy.moudles.examSeason.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.examSeason.entity.ExamSeason;

/** 
 * ExamSeason数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@MyBatisDao
public interface ExamSeasonDao {

	/**
	 * 新增ExamSeason对象
	 *
	 * @param ExamSeason
	 */
	public void insertExamSeason(ExamSeason ExamSeason);
	
	/**
	 * 更新ExamSeason对象
	 *
	 * @param ExamSeason
	 */
	public void updateExamSeason(ExamSeason ExamSeason);
	
	/**
	 * 根据ID获取ExamSeason对象
	 *
	 * @param id
	 */
	public ExamSeason getExamSeasonById(String id);
	
	/**
	 * 根据过滤条件获取ExamSeason列表对象
	 *
	 * @param filter
	 */
	public List<ExamSeason> queryExamSeasonsFilter(Map<String, Object> filter);
	
	/**
	 * 根据ksbh删除ExamSeason列表对象
	 *
	 * @param id
	 */
	public void deleteExamSeasonByExamNo(String ExamNo);
	
	/**
	 * 根据Id集合批量删除ExamSeason列表对象
	 *
	 * @param ids
	 */
	public void deleteExamSeasons(List<String> ids);
	
	/**
	 * 批量添加ExamSeason列表对象
	 *
	 * @param ids
	 */
	public void insertExamSeasons(List<ExamSeason> examSeasons);
	
	
	
}



