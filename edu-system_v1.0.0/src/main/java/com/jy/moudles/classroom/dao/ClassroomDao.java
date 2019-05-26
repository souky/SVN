package com.jy.moudles.classroom.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.moudles.classroom.entity.Classroom;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * classroom数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface ClassroomDao {

	/**
	 * 新增classroom对象
	 *
	 * @param classroom
	 */
	public void insertClassroom(Classroom classroom);
	
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
	public Classroom getClassroomByName(String name,String orgId);
	
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
	
	
	public List<Classroom> getClassroomByNames(@Param("list")List<String> list,@Param("orgId")String orgId);
	
}



