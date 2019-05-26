package com.jy.moudles.classroom.service;

import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.user.entity.User;

import java.util.List;
import java.util.Map;

/** 
 * classroom业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface ClassroomService {

	/**
	 * 新增classroom对象
	 *
	 * @param classroom
	 */
	public void insertClassroom(User user, Classroom classroom);
	
	/**
	 * 更新classroom对象
	 *
	 * @param classroom
	 */
	public void updateClassroom(Classroom classroom);
	
	/**
	 * 根据ID获取classroom对象
	 *
	 * @param id
	 */
	public Classroom getClassroomById(String id);
	
	/**
	 * 根据ID获取classroom对象 优化
	 *
	 * @param id
	 */
	public Classroom getClassroomByIdNew(String id);
	
	/**
	 * 根据name获取classroom对象
	 *
	 * @param name
	 */
	public Classroom getClassroomByName(String name, String orgId);

	/**
	 * 根据过滤条件获取classroom列表对象
	 *
	 * @param filter
	 */
	public List<Classroom> queryClassroomsFilter(Map<String, Object> filter);

	/**
	 * 根据过滤条件获取classroom列表对象
	 *
	 * @param filter
	 */
	public List<Classroom> queryClassroomsFilterNew(Map<String, Object> filter);

	/**
	 * 根据Id删除classroom列表对象
	 *
	 * @param id
	 */
	public void deleteClassroomById(String id);
	
	/**
	 * 根据Id集合批量删除classroom列表对象
	 *
	 * @param ids
	 */
	public void deleteClassrooms(List<String> ids);
	
	public List<Classroom> queryClassroomsByGrade(Classroom classroom);
	
	public List<Classroom> getClassroomByNames(List<String> list,String orgId);
	
}

