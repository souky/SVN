package com.jy.moudles.buttInterface.entity;

import com.jy.common.entity.BaseEntity;
/**
 * 学生考试信息
 * @author Administrator
 *
 */
public class StudentExam extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**主键id**/
	private String id;
	/**考试id**/
	private String examId;
	/**学生考试号**/
	private String examStuNo;
	/**学校id**/
	private String schoolId;
	/**组织id**/
	private String orgId;
	/**班级id**/
	private String classroomId;
	/**学校名称**/
	private String schoolName;
	/**班级名称**/
	private String classroomName;
	/**年级编码**/
	private String gradeCode;
	/**学生名称**/
	private String studentName;
	/**学生学号**/
	private String studentNo;
	/**学生id**/
	private String studentId;
	/**同步状态 0未同步 1已同步**/
	private Integer synchronousState;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getExamStuNo() {
		return examStuNo;
	}
	public void setExamStuNo(String examStuNo) {
		this.examStuNo = examStuNo;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getClassroomId() {
		return classroomId;
	}
	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getClassroomName() {
		return classroomName;
	}
	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Integer getSynchronousState() {
		return synchronousState;
	}
	public void setSynchronousState(Integer synchronousState) {
		this.synchronousState = synchronousState;
	}
	
	
}
