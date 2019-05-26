package com.jy.moudles.buttInterface.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.buttInterface.entity.StudentExam;
import com.jy.moudles.buttInterface.entity.TwoWaySpecificationDetailNew;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.teacher.entity.Teacher;

public interface ButtInterfaceService {
	/**
	 * 根据条件查询考试信息
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月25日 下午1:41:48
	 */
	public List<Exam> queryExamInfoMsg(Map<String,Object> param);
	
	/**
	 * 根据条件查询场考试的所有科目双向细目表
	 * @param param
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月26日 上午9:42:46
	 */
	public Map<String,List<TwoWaySpecificationDetailNew>> queryTwoWaySpecificationMsg(Map<String,Object> param);
	
	/**
	 * 根据条件查询教师信息
	 * @param param
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月26日 上午10:28:35
	 */
	public List<Teacher> queryTeachermsg(Map<String,Object> param);
	
	/**
	 * 学生考试信息
	 * @param param
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月26日 下午2:06:14
	 */
	public List<StudentExam> queryStudentExamMsg(Map<String,Object> param);
	
	/**
	 * 修改考试信息
	 * @param param
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月30日 下午1:48:46
	 */
	public int updateExamInfo(Map<String,Object> param);
	
	/**
	 * 接收信息并批量插入数据库
	 * @param param
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月31日 下午5:21:55
	 */
	public int receiveScore(List<Map<String,Object>> list);
	
	/**
	 * 接收试卷信息并批量插入数据库
	 * @param param
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年2月1日 上午9:31:17
	 */
	public int receiveScoreMsg(Map<String,List<Map<String,Object>>> param);
	
	/**
	 * 修改考试状态 定时任务用 
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年2月8日 上午10:04:06
	 */
	public int updateExamStatus();
}
