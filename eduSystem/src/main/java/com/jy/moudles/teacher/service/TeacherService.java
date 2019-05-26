package com.jy.moudles.teacher.service;

import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.user.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * teacher业务接口
 * 创建人：1
 * 创建时间：2017-11-30
 */
public interface TeacherService {

	/**
	 * 新增teacher对象
	 *
	 * @param teacher
	 */
	public void insertTeacher(User currentUser, Teacher teacher);
	
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
	 * 批量插入teacher 对象
	 *
	 * @param teacherLists
	 */
	void insertTeacherListBatch(List<ArrayList<String>> teacherLists);

	/**
	 * 导出老师名单
	 *
	 * @param response
	 * @param filter
	 */
	void getTeacherLists(HttpServletResponse response, Map<String, Object> filter);
}

