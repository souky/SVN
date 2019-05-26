package com.jy.moudles.examSpecificationRelation.entity;

import com.jy.common.entity.BaseEntity;

public class ExamSpecificationRelation extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 考试ID
	 */
	private String examId;
	
	/**
	 * 双向细目表ID
	 */
	private String spId;

	/**
	 * 双向细目表名称
	 */
	private String spName;

	/**
	 * 双向细目表科目
	 */
	private String subjectCode;

	/**
	 * 双向细目表年级
	 */
	private String gradeCode;

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
	
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
}



