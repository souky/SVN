package com.jy.moudles.examPlaceInfo.entity;

import java.util.List;
import java.util.Map;

import com.jy.common.entity.BaseEntity;

public class ExamPlaceInfo extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -587756151207898699L;

	/**
	 * 考试编号
	 */
	private String examNo;
	
	/**
	 * 考试场次编号
	 */
	private String examSeasonNo;
	
	/**
	 * 场所编号
	 */
	private String csbh;
	
	/**
	 * 设备编号
	 */
	private String sbbh;
	
	private List<Map<String,Object>> sb;
	
	
	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	
	public String getExamSeasonNo() {
		return examSeasonNo;
	}

	public void setExamSeasonNo(String examSeasonNo) {
		this.examSeasonNo = examSeasonNo;
	}
	
	public String getCsbh() {
		return csbh;
	}

	public void setCsbh(String csbh) {
		this.csbh = csbh;
	}
	
	public String getSbbh() {
		return sbbh;
	}

	public void setSbbh(String sbbh) {
		this.sbbh = sbbh;
	}

	public List<Map<String,Object>> getSb() {
		return sb;
	}

	public void setSb(List<Map<String,Object>> sb) {
		this.sb = sb;
	}
	
}



