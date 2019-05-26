package com.jy.moudles.examPlaceInfo.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.examPlaceInfo.entity.ExamPlaceInfo;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * ExamPlaceInfo数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@MyBatisDao
public interface ExamPlaceInfoDao {

	/**
	 * 新增ExamPlaceInfo对象
	 *
	 * @param ExamPlaceInfo
	 */
	public void insertExamPlaceInfo(ExamPlaceInfo ExamPlaceInfo);
	
	/**
	 * 更新ExamPlaceInfo对象
	 *
	 * @param ExamPlaceInfo
	 */
	public void updateExamPlaceInfo(ExamPlaceInfo ExamPlaceInfo);
	
	/**
	 * 根据ID获取ExamPlaceInfo对象
	 *
	 * @param id
	 */
	public ExamPlaceInfo getExamPlaceInfoById(String id);
	
	/**
	 * 根据过滤条件获取ExamPlaceInfo列表对象
	 *
	 * @param filter
	 */
	public List<ExamPlaceInfo> queryExamPlaceInfosFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除ExamPlaceInfo列表对象
	 *
	 * @param id
	 */
	public void deleteExamPlaceInfoByExamNo(String ExamNo);
	
	/**
	 * 根据Id集合批量删除ExamPlaceInfo列表对象
	 *
	 * @param ids
	 */
	public void deleteExamPlaceInfos(List<String> ids);
	
	/**
	 * 批量添加ExamPlaceInfo列表对象
	 *
	 * @param ids
	 */
	public void insertExamPlaceInfos(List<ExamPlaceInfo> list);
	
	
	
}



