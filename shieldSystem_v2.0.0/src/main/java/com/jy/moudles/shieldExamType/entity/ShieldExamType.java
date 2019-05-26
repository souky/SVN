package com.jy.moudles.shieldExamType.entity;

import com.jy.common.entity.BaseEntity;
/**
*考试类型表
*/
public class ShieldExamType extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 考试类型名称
	 */
	private String examTypeName;
	
	/**
	 * 考试类型编号
	 */
	private String examTypeNo;
	
	
	public String getExamTypeName() {
		return examTypeName;
	}

	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}
	
	public String getExamTypeNo() {
		return examTypeNo;
	}

	public void setExamTypeNo(String examTypeNo) {
		this.examTypeNo = examTypeNo;
	}
	
}



