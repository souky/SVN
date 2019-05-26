package com.jy.moudles.knowledgePoint.entity;

import com.jy.common.entity.BaseEntity;

public class KnowledgePoint extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 知识点编码
	 */
	private String knowledgeCode;
	
	/**
	 * 知识点内容
	 */
	private String knowledgeContent;
	
	/**
	 * 学科
	 */
	private String subjectId;
	
	/**
	 * 父ID
	 */
	private String parentId;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	private String gradeCode;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	
	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	
	public String getKnowledgeContent() {
		return knowledgeContent;
	}

	public void setKnowledgeContent(String knowledgeContent) {
		this.knowledgeContent = knowledgeContent;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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
	
}



