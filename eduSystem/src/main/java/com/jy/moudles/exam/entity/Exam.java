package com.jy.moudles.exam.entity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.jy.common.entity.BaseEntity;

public class Exam extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**同步状态 0未同步 1已同步**/
	private Integer synchronousState;
	/**学校id**/
	private String schoolId;
	/**
	 * 考试名称
	 */
	private String examName;
	/**
	 * 年级编码
	 */
	private String gradeCode;
	/**
	 * 考试开始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date examStartDate;
	
	/**
	 * 考试结束时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date examEndDate;
	
	/**
	 * 班级
	 */
	private String classroom;
	
	/**
	 * 科目
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
	
	
	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public Date getExamStartDate() {
		return examStartDate;
	}

	public void setExamStartDate(Date examStartDate) {
		this.examStartDate = examStartDate;
	}
	
	public Date getExamEndDate() {
		return examEndDate;
	}

	public void setExamEndDate(Date examEndDate) {
		this.examEndDate = examEndDate;
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
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getSynchronousState() {
		return synchronousState;
	}

	public void setSynchronousState(Integer synchronousState) {
		this.synchronousState = synchronousState;
	}
	
}



