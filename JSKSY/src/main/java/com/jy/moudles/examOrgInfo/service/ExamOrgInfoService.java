package com.jy.moudles.examOrgInfo.service;

import com.jy.moudles.examOrgInfo.entity.ExamOrgInfo;
import java.util.List;
import java.util.Map;

/** 
 * ExamOrgInfo业务接口
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
public interface ExamOrgInfoService {

	/**
	 * 新增ExamOrgInfo对象
	 *
	 * @param ExamOrgInfo
	 */
	public void insertExamOrgInfo(ExamOrgInfo ExamOrgInfo);
	
	/**
	 * 更新ExamOrgInfo对象
	 *
	 * @param ExamOrgInfo
	 */
	public void updateExamOrgInfo(ExamOrgInfo ExamOrgInfo);
	
	/**
	 * 根据ID获取ExamOrgInfo对象
	 *
	 * @param id
	 */
	public ExamOrgInfo getExamOrgInfoById(String id);
	
	/**
	 * 根据过滤条件获取ExamOrgInfo列表对象
	 *
	 * @param filter
	 */
	public List<ExamOrgInfo> queryExamOrgInfosFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除ExamOrgInfo列表对象
	 *
	 * @param id
	 */
	public void deleteExamOrgInfoById(String id);
	
	/**
	 * 根据Id集合批量删除ExamOrgInfo列表对象
	 *
	 * @param ids
	 */
	public void deleteExamOrgInfos();
	
	/**
	 * 批量新增ExamOrgInfo对象
	 *
	 * @param ExamOrgInfo
	 */
	public void insertExamOrgInfos(List<ExamOrgInfo> examOrgInfos);
	
}

