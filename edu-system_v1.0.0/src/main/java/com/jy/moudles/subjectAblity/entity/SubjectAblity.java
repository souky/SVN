package com.jy.moudles.subjectAblity.entity;

import com.jy.common.entity.BaseEntity;

public class SubjectAblity extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 科目名称
	 */
	private String subjectName;
	
	/**
	 * 能力值名称
	 */
	private String ablityName;
	
	/**
	 * 组织机构id
	 */
	private String orgId;
	
	/**
	 * 排序
	 */
	private int soft;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public String getAblityName() {
		return ablityName;
	}

	public void setAblityName(String ablityName) {
		this.ablityName = ablityName;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public int getSoft() {
		return soft;
	}

	public void setSoft(int soft) {
		this.soft = soft;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}



