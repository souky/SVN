package com.jy.moudles.teacher.entity;

import com.jy.common.entity.BaseEntity;

public class Teacher extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 老师姓名
	 */
	private String teacherName;

	/**
	 * 老师性别（1 男 0 女）
	 */
	private int teacherSex;

	/**
	 * 老师年龄
	 */
	private Integer teacherAge;

	/**
	 * 老师手机号
	 */
	private String teacherMobile;

	/**
	 * 教师职务
	 */
	private String teacherDuty;

	/**
	 * 教师职称
	 */
	private String teacherJobTitle;

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
	 * 班级
	 */
	private String classroom;

	/**
	 * 学科
	 */
	private String subject;
	
	/**年级编码**/
	private String gradeCode;
	
	/**
	 * 是否是班主任 0:不是 1:是
	 */
	private int isClassTeacher;

	// 新加字段
	private String[] classArray;
	
	private String[] subjectArray;
	
	private String grade;

	private String schoolName;
	
	private String userName;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String[] getClassArray() {
		return classArray;
	}

	public void setClassArray(String[] classArray) {
		this.classArray = classArray;
	}

	public String[] getSubjectArray() {
		return subjectArray;
	}

	public void setSubjectArray(String[] subjectArray) {
		this.subjectArray = subjectArray;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getTeacherSex() {
		return teacherSex;
	}

	public void setTeacherSex(int teacherSex) {
		this.teacherSex = teacherSex;
	}

	public Integer getTeacherAge() {
		return teacherAge;
	}

	public void setTeacherAge(Integer teacherAge) {
		this.teacherAge = teacherAge;
	}

	public String getTeacherMobile() {
		return teacherMobile;
	}

	public void setTeacherMobile(String teacherMobile) {
		this.teacherMobile = teacherMobile;
	}

	public String getTeacherDuty() {
		return teacherDuty;
	}

	public void setTeacherDuty(String teacherDuty) {
		this.teacherDuty = teacherDuty;
	}

	public String getTeacherJobTitle() {
		return teacherJobTitle;
	}

	public void setTeacherJobTitle(String teacherJobTitle) {
		this.teacherJobTitle = teacherJobTitle;
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

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getIsClassTeacher() {
		return isClassTeacher;
	}

	public void setIsClassTeacher(int isClassTeacher) {
		this.isClassTeacher = isClassTeacher;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	
}
