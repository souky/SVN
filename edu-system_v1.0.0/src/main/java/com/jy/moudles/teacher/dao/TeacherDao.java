package com.jy.moudles.teacher.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.teacher.entity.Teacher;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * teacher数据接口
 * 创建人：1
 * 创建时间：2017-11-30
 */
@MyBatisDao
public interface TeacherDao {

	/**
	 * 新增teacher对象
	 *
	 * @param teacher
	 */
	public void insertTeacher(Teacher teacher);
	
	/**
	 * 更新teacher对象
	 *
	 * @param teacher
	 */
	public void updateTeacher(Teacher teacher);
	
	/**
	 * 根据ID获取teacher对象
	 *
	 * @param id
	 */
	public Teacher getTeacherById(String id);
	
	/**
	 * 根据过滤条件获取teacher列表对象
	 *
	 * @param filter
	 */
	public List<Teacher> queryTeachersFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除teacher列表对象
	 *
	 * @param id
	 */
	public void deleteTeacherById(String id);
	
	/**
	 * 根据Id集合批量删除teacher列表对象
	 *
	 * @param ids
	 */
	public void deleteTeachers(List<String> ids);

	/**
	 * 批量新增teacherList 对象
	 *
	 * @param teacherLists
	 */
	void insertTeacherListBatch(List<Teacher> teacherLists);
	
}



