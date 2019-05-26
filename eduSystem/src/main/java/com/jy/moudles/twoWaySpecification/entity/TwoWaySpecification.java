package com.jy.moudles.twoWaySpecification.entity;


import com.jy.common.entity.BaseEntity;

public class TwoWaySpecification extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 双向细目表名称
	 */
	private String specificationName;
	
	/**
	 * 科目Code
	 */
	private String subjectCode;
	
	/**
	 * 年级编码
	 */
	private String gradeCode;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;

	/**
	 * 总分
	 */
	private int score;

	/**
	 * 总题数
	 */
	private int totalQuestionNum;

	/**
	 * 客观题题数
	 */
	private int objectiveQuesNum;

	/**
	 * 主观题题数
	 */
	private int subjectiveQuesNum;

	/**
	 * 备注
	 */
	private String remark;
	
	/*
	 * 考试Id
	 */
	private String examId;
	
	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
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
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotalQuestionNum() {
		return totalQuestionNum;
	}

	public void setTotalQuestionNum(int totalQuestionNum) {
		this.totalQuestionNum = totalQuestionNum;
	}

	public int getObjectiveQuesNum() {
		return objectiveQuesNum;
	}

	public void setObjectiveQuesNum(int objectiveQuesNum) {
		this.objectiveQuesNum = objectiveQuesNum;
	}

	public int getSubjectiveQuesNum() {
		return subjectiveQuesNum;
	}

	public void setSubjectiveQuesNum(int subjectiveQuesNum) {
		this.subjectiveQuesNum = subjectiveQuesNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}



