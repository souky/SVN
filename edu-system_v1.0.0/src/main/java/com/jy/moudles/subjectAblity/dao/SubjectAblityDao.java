package com.jy.moudles.subjectAblity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.moudles.subjectAblity.entity.SubjectAblity;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * subjectAblity数据接口
 * 创建人：1
 * 创建时间：2018-01-08
 */
@MyBatisDao
public interface SubjectAblityDao {

	/**
	 * 新增subjectAblity对象
	 *
	 * @param subjectAblity
	 */
	public void insertSubjectAblity(SubjectAblity subjectAblity);
	
	/**
	 * 更新subjectAblity对象
	 *
	 * @param subjectAblity
	 */
	public void updateSubjectAblity(SubjectAblity subjectAblity);
	
	/**
	 * 根据ID获取subjectAblity对象
	 *
	 * @param id
	 */
	public SubjectAblity getSubjectAblityById(String id);
	
	/**
	 * 根据过滤条件获取subjectAblity列表对象
	 *
	 * @param filter
	 */
	public List<SubjectAblity> querySubjectAblitysFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除subjectAblity列表对象
	 *
	 * @param id
	 */
	public void deleteSubjectAblityById(String id);
	
	/**
	 * 根据Id集合批量删除subjectAblity列表对象
	 *
	 * @param ids
	 */
	public void deleteSubjectAblitys(List<String> ids);
	
	public void deleteSubjectAblityBySubject(@Param("subjectName")String subjectName,@Param("orgId")String orgId);
	
}



