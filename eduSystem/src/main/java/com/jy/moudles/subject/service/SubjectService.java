package com.jy.moudles.subject.service;

import com.jy.moudles.subject.entity.Subject;
import java.util.List;
import java.util.Map;

/** 
 * subject业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface SubjectService {

	/**
	 * 新增subject对象
	 *
	 * @param subject
	 */
	public void insertSubject(Subject subject);
	
	/**
	 * 更新subject对象
	 *
	 * @param subject
	 */
	public void updateSubject(Subject subject);
	
	/**
	 * 根据ID获取subject对象
	 *
	 * @param id
	 */
	public Subject getSubjectById(String id);
	
	/**
	 * 根据过滤条件获取subject列表对象
	 *
	 * @param filter
	 */
	public List<Subject> querySubjectsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除subject列表对象
	 *
	 * @param id
	 */
	public void deleteSubjectById(String id);
	
	/**
	 * 根据Id集合批量删除subject列表对象
	 *
	 * @param ids
	 */
	public void deleteSubjects(List<String> ids);
	
	public List<Subject> querySubjectsByNames(List<String> list);
	
}

