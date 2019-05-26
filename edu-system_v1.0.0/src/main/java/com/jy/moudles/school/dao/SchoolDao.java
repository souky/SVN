package com.jy.moudles.school.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.school.entity.School;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * school数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface SchoolDao {

	/**
	 * 新增school对象
	 *
	 * @param school
	 */
	public void insertSchool(School school);
	
	/**
	 * 更新school对象
	 *
	 * @param school
	 */
	public void updateSchool(School school);
	
	/**
	 * 根据ID获取school对象
	 *
	 * @param id
	 */
	public School getSchoolById(String id);
	
	/**
	 * 根据过滤条件获取school列表对象
	 *
	 * @param filter
	 */
	public List<School> querySchoolsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除school列表对象
	 *
	 * @param id
	 */
	public void deleteSchoolById(String id);
	
	/**
	 * 根据Id集合批量删除school列表对象
	 *
	 * @param ids
	 */
	public void deleteSchools(List<String> ids);

	/**
	 * 根据schoolId获取subject_code
	 *
	 * @param schoolId
	 */
	public List<String> getSubjectById(String schoolId);
	
	public School getSchoolByOrgId(String orgId);
	
}



