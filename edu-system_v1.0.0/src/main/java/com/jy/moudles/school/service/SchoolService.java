package com.jy.moudles.school.service;

import com.jy.moudles.school.entity.School;
import java.util.List;
import java.util.Map;

/** 
 * school业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface SchoolService {

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

