package com.jy.moudles.subject.entity;

import com.jy.common.entity.BaseEntity;

public class Subject extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 科目编码
	 */
	private int subjectCode;

	/**
	 * 科目名称
	 */

	private String subjectName;
	
	/**
	 * 颜色
	 */
	private String subjectColor;
	
	/**
	 * 学校ID
	 */
	private String schoolId;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 备注
	 */
	private String remark;

	public int getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(int subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public String getSubjectColor() {
		return subjectColor;
	}

	public void setSubjectColor(String subjectColor) {
		this.subjectColor = subjectColor;
	}
	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
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



