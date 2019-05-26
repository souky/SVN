package com.jy.moudles.student.service;

import com.jy.moudles.score.entity.Score;
import com.jy.moudles.student.entity.Student;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * student业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface StudentService {

	/**
	 * 新增student对象
	 *
	 * @param student
	 */
	public void insertStudent(Student student);
	
	/**
	 * 批量插入student对象
	 *
	 * @param students
	 */
	public void insertStudents(List<ArrayList<String>> students);
	
	public void insertStudentsNew(List<Student> students);
	public void insertScores(List<Score> score);
	/**
	 * 更新student对象
	 *
	 * @param student
	 */
	public void updateStudent(Student student);
	
	/**
	 * 根据ID获取student对象
	 *
	 * @param id
	 */
	public Student getStudentById(String id);
	
	/**
	 * 根据ID获取student对象 优化
	 * @param id
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月16日 上午10:11:49
	 */
	public Student getStudentByIdNew(String id);
	
	/**
	 * 根据sno获取student对象
	 *
	 * @param sno
	 * @param orgId
	 */
	public Student getStudentByStuNo(String sno, String orgId);

	/**
	 * 根据过滤条件获取student列表对象
	 *
	 * @param filter
	 */
	public List<Student> queryStudentsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除student列表对象
	 *
	 * @param id
	 */
	public void deleteStudentById(String id);
	
	/**
	 * 根据Id集合批量删除student列表对象
	 *
	 * @param ids
	 */
	public void deleteStudents(List<String> ids);
	
	public void getStudentLists(HttpServletResponse response,Map<String, Object> filter);
	
}

