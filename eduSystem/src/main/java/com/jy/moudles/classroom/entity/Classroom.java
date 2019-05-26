package com.jy.moudles.classroom.entity;

import com.jy.common.entity.BaseEntity;

public class Classroom extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 班级名称
	 */
	private String classroomName;
	
	/**
	 * 年级编码
	 */
	private int gradeCode;
	
	/**
	 * 班级编码
	 */
	private int classCode;

	/**
	 * 班主任
	 */
	private String classTeacherId;
	
	/**
	 * 学校ID
	 */
	private String schoolId;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 老师（班主任）姓名
	 */
	private String teacherName;
	
	/**
	 * 班级人数
	 */
	private int studentCount;
	
	/**
	 * 班级对应的年级
	 */
	private String grade;
	
	private String position;
	
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}
	
	public int getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(int gradeCode) {
		this.gradeCode = gradeCode;
	}

	public int getClassCode() {
		return classCode;
	}

	public void setClassCode(int classCode) {
		this.classCode = classCode;
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
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClassTeacherId() {
		return classTeacherId;
	}

	public void setClassTeacherId(String classTeacherId) {
		this.classTeacherId = classTeacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}



