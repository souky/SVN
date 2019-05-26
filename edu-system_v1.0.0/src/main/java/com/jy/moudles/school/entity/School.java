package com.jy.moudles.school.entity;

import com.jy.common.entity.BaseEntity;

public class School extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 学校名称
	 */
	private String schoolName;
	
	/**
	 * 学段
	 */
	private String schoolPeriod;
	
	/**
	 * 学校地址
	 */
	private String schoolAddr;
	
	/**
	 * 学校手机号
	 */
	private String schoolMobile;
	
	/**
	 * 学校座机号
	 */
	private String schoolTel;
	
	/**
	 * 学校唯一码
	 */
	private String schoolOnlyCode;
	
	/**
	 * 学校联系人
	 */
	private String schoolContact;
	
	/**
	 * 学校联系人手机号
	 */
	private String schoolContactMobile;
	
	/**
	 * 学校联系人座机号
	 */
	private String schoolContactTel;
	
	/**
	 * 科目code
	 */
	private String subject;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	//新加字段
	private String[] periodArray;
	private String[] subjectArray;
	
	
	
	public String[] getPeriodArray() {
		return periodArray;
	}

	public void setPeriodArray(String[] periodArray) {
		this.periodArray = periodArray;
	}

	public String[] getSubjectArray() {
		return subjectArray;
	}

	public void setSubjectArray(String[] subjectArray) {
		this.subjectArray = subjectArray;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getSchoolPeriod() {
		return schoolPeriod;
	}

	public void setSchoolPeriod(String schoolPeriod) {
		this.schoolPeriod = schoolPeriod;
	}
	
	public String getSchoolAddr() {
		return schoolAddr;
	}

	public void setSchoolAddr(String schoolAddr) {
		this.schoolAddr = schoolAddr;
	}
	
	public String getSchoolMobile() {
		return schoolMobile;
	}

	public void setSchoolMobile(String schoolMobile) {
		this.schoolMobile = schoolMobile;
	}
	
	public String getSchoolTel() {
		return schoolTel;
	}

	public void setSchoolTel(String schoolTel) {
		this.schoolTel = schoolTel;
	}
	
	public String getSchoolOnlyCode() {
		return schoolOnlyCode;
	}

	public void setSchoolOnlyCode(String schoolOnlyCode) {
		this.schoolOnlyCode = schoolOnlyCode;
	}
	
	public String getSchoolContact() {
		return schoolContact;
	}

	public void setSchoolContact(String schoolContact) {
		this.schoolContact = schoolContact;
	}
	
	public String getSchoolContactMobile() {
		return schoolContactMobile;
	}

	public void setSchoolContactMobile(String schoolContactMobile) {
		this.schoolContactMobile = schoolContactMobile;
	}
	
	public String getSchoolContactTel() {
		return schoolContactTel;
	}

	public void setSchoolContactTel(String schoolContactTel) {
		this.schoolContactTel = schoolContactTel;
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



