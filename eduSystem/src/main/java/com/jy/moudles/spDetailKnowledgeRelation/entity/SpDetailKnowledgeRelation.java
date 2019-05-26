package com.jy.moudles.spDetailKnowledgeRelation.entity;

import com.jy.common.entity.BaseEntity;

public class SpDetailKnowledgeRelation extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 双向细目详细表ID
	 */
	private String spDetailId;
	
	/**
	 * 知识点ID
	 */
	private String knowledgePointId;
	
	/**
	 * 知识点内容
	 */
	private String knowledgeContent;

	/**
	 * 备注
	 */
	private String remark;
	
	
	public String getSpDetailId() {
		return spDetailId;
	}

	public void setSpDetailId(String spDetailId) {
		this.spDetailId = spDetailId;
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

	public String getKnowledgeContent() {
		return knowledgeContent;
	}

	public void setKnowledgeContent(String knowledgeContent) {
		this.knowledgeContent = knowledgeContent;
	}
}



