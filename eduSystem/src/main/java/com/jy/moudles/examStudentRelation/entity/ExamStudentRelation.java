package com.jy.moudles.examStudentRelation.entity;

import com.jy.common.entity.BaseEntity;

public class ExamStudentRelation extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 考试ID
	 */
	private String examId;

	/**
	 * 考试名称
	 */
	private String examName;

	/**
	 * 学生ID
	 */
	private String studentId;
	
	/**
	 * 学生学号
	 */
	private String studentNo;

	/**
	 * 考生编号
	 */
	private String examStuNo;

	/**
	 * 学生姓名
	 */
	private String studentName;

	/**
	 * 考试状态(0:缺考,1:参考)
	 */
	private int status;

	/**
	 * 班级
	 */
	private String classroom;

	/**
	 * 学科
	 */
	private String subject;

	/**
	 * 考试状态:未开始、正在进行、已结束（0、1、2）
	 */
	private int examStatus;

	/**
	 * 组织机构ID
	 */
	private String orgId;

	/**
	 * 备注
	 */
	private String remark;
	
	
	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getExamStuNo() {
		return examStuNo;
	}

	public void setExamStuNo(String examStuNo) {
		this.examStuNo = examStuNo;
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

	public int getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(int examStatus) {
		this.examStatus = examStatus;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}



