package com.jy.moudles.examOrgInfo.entity;

import com.jy.common.entity.BaseEntity;

public class ExamOrgInfo extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1858624156616632701L;

	/**
	 * 考试编号
	 */
	private String examNo;
	
	/**
	 * 机构代码
	 */
	private String jgdm;
	
	/**
	 * 机构名称
	 */
	private String jgmc;
	
	
	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	
	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	
	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}
	
}



