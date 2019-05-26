package com.jy.moudles.student.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.student.entity.Student;

/** 
 * student数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface StudentDao {

	/**
	 * 新增student对象
	 *
	 * @param student
	 */
	public void insertStudent(Student student);
	
	/**
	 * 批量插入
	 * @param students
	 */
	public void insertStudents(List<Student> students);
	
	/**
	 * 批量插入 新
	 */
	public void insertStudentsNew(List<Student> students);
	
	public void insertScores(List<Score> scoreList);
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
	 * Create on 2018年1月16日 上午10:11:04
	 */
	public Student getStudentByIdNew(String id);
	/**
	 * 根据sno获取student对象
	 *
	 * @param sno
	 * @param orgId
	 */
	public Student getStudentByStuNo(@Param("sno")String sno, @Param("orgId")String orgId);

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



