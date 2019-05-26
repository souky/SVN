package com.jy.moudles.buttInterface.entity;

import com.jy.common.entity.BaseEntity;

public class TwoWaySpecificationDetailNew extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**主键id**/
	private String id;
	/**题号**/
	private int itemNo;
	/**题型( 0 主观题、 1客观题)**/
	private int itemType;
	/**小题满分**/
	private int itemScore;
	/**标准答案**/
	private String itemAnswer;
	/**能力值**/
	private String itemAbility;
	/**父ID**/
	private String parentId;
	/**组织机构ID**/
	private String orgId;
	/**知识点id**/
	private String knowledgeId;
	/**备注**/
	private String remark;
	/**辅助字段  考试id**/
	private String examId;
	/**辅助字段  科目编码**/
	private String subjectCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	
	
	
}
