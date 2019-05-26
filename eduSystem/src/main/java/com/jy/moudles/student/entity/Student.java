package com.jy.moudles.student.entity;

import com.jy.common.entity.BaseEntity;

public class Student extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 学生姓名
	 */
	private String studentName;
	/**
	 * 学号
	 */
	private String studentNo;
	
	/**
	 * 学生性别
	 */
	private int studentSex;
	
	/**
	 * 学生年龄
	 */
	private Integer studentAge;
	
	/**
	 * 入学学年
	 */
	private int schoolYear;
	
	/**
	 * 联系人
	 */
	private String studentContact;
	
	/**
	 * 联系方式
	 */
	private String studentContactMobile;
	
	/**
	 * 班级id
	 */
	private String classroomId;
	
	/**
	 * 学校ID
	 */
	private String schoolId;

	/**
	 * 班级名称
	 */
	private String classroomName;
	
	/**
	 * 学校名称
	 */
	private String schoolName;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String userName;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public int getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(int studentSex) {
		this.studentSex = studentSex;
	}
	
	public Integer getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(Integer studentAge) {
		this.studentAge = studentAge;
	}
	
	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	public String getStudentContact() {
		return studentContact;
	}

	public void setStudentContact(String studentContact) {
		this.studentContact = studentContact;
	}
	
	public String getStudentContactMobile() {
		return studentContactMobile;
	}

	public void setStudentContactMobile(String studentContactMobile) {
		this.studentContactMobile = studentContactMobile;
	}
	
	public String getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
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

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}



