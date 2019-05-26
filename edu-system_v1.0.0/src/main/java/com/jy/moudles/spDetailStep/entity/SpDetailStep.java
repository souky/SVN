package com.jy.moudles.spDetailStep.entity;

import com.jy.common.entity.BaseEntity;

public class SpDetailStep extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 双向细目表id
	 */
	private String spDetailId;
	
	private String spId;
	
	/**
	 * 步骤分
	 */
	private int stepScore;
	
	/**
	 * 步骤答案
	 */
	private String stepAnswer;
	
	/**
	 * 知识点id
	 */
	private String knowledgePointId;
	/**
	 * 知识点id
	 */
	private String knowledgePointName;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String sort;
	
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSpDetailId() {
		return spDetailId;
	}

	public void setSpDetailId(String spDetailId) {
		this.spDetailId = spDetailId;
	}
	
	public int getStepScore() {
		return stepScore;
	}

	public void setStepScore(int stepScore) {
		this.stepScore = stepScore;
	}
	
	public String getStepAnswer() {
		return stepAnswer;
	}

	public void setStepAnswer(String stepAnswer) {
		this.stepAnswer = stepAnswer;
	}
	
	public String getKnowledgePointId() {
		return knowledgePointId;
	}

	public void setKnowledgePointId(String knowledgePointId) {
		this.knowledgePointId = knowledgePointId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getKnowledgePointName() {
		return knowledgePointName;
	}

	public void setKnowledgePointName(String knowledgePointName) {
		this.knowledgePointName = knowledgePointName;
	}
	
}



