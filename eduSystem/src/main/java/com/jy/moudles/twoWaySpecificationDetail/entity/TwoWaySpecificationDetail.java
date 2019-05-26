package com.jy.moudles.twoWaySpecificationDetail.entity;

import java.util.List;

import com.jy.common.entity.BaseEntity;
import com.jy.moudles.spDetailStep.entity.SpDetailStep;

public class TwoWaySpecificationDetail extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 题号
	 */
	private int itemNo;
	
	/**
	 * 题型(0、客观题 1、主观题)
	 */
	private int itemType;
	
	/**
	 * 小题满分
	 */
	private int itemScore;
	
	/**
	 * 标准答案
	 */
	private String itemAnswer;
	
	/**
	 * 能力值
	 */
	private String itemAbility;
	
	/**
	 * 父ID
	 */
	private String parentId;
	
	private String knowledgeId;
	private String knowledgeName;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private List<SpDetailStep> stepList;
	
	
	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	
	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	public int getItemScore() {
		return itemScore;
	}

	public void setItemScore(int itemScore) {
		this.itemScore = itemScore;
	}
	
	public String getItemAnswer() {
		return itemAnswer;
	}

	public void setItemAnswer(String itemAnswer) {
		this.itemAnswer = itemAnswer;
	}
	
	public String getItemAbility() {
		return itemAbility;
	}

	public void setItemAbility(String itemAbility) {
		this.itemAbility = itemAbility;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public List<SpDetailStep> getStepList() {
		return stepList;
	}

	public void setStepList(List<SpDetailStep> stepList) {
		this.stepList = stepList;
	}
	
}



