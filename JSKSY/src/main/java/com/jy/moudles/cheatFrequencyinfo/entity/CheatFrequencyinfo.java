package com.jy.moudles.cheatFrequencyinfo.entity;

import com.jy.common.entity.BaseEntity;

public class CheatFrequencyinfo extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5516068385011499692L;

	/**
	 * 考试计划编号
	 */
	private String examNo;
	
	/**
	 * 考试场次编号
	 */
	private String examSeasonNo;
	
	/**
	 * 频率(只传入数字，单位：千赫兹KHz)
	 */
	private String pl;
	
	/**
	 * 信号强度(只传入数字，单位：dBm)
	 */
	private String qd;
	
	/**
	 * 信息类型 1：语音  2：数传
	 */
	private String xxlx;
	
	/**
	 * 信号类型，文本描述
	 */
	private String xhlx;
	
	/**
	 * 确认标记类型 1：作弊，99：其它
	 */
	private String qrbj;
	
	/**
	 * 侦测设备硬件序列号
	 */
	private String sbbh;
	
	/**
	 * 还原文件名
	 */
	private String wjm;
	
	/**
	 * 还原文件内容，转成base64
	 */
	private String wj;
	
	/**
	 * 厂商代码
	 */
	private String sbcsdm;
	
	
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
	
	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}
	
	public String getQd() {
		return qd;
	}

	public void setQd(String qd) {
		this.qd = qd;
	}
	
	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}
	
	public String getXhlx() {
		return xhlx;
	}

	public void setXhlx(String xhlx) {
		this.xhlx = xhlx;
	}
	
	public String getQrbj() {
		return qrbj;
	}

	public void setQrbj(String qrbj) {
		this.qrbj = qrbj;
	}
	
	public String getSbbh() {
		return sbbh;
	}

	public void setSbbh(String sbbh) {
		this.sbbh = sbbh;
	}
	
	public String getWjm() {
		return wjm;
	}

	public void setWjm(String wjm) {
		this.wjm = wjm;
	}
	
	public String getWj() {
		return wj;
	}

	public void setWj(String wj) {
		this.wj = wj;
	}
	
	public String getSbcsdm() {
		return sbcsdm;
	}

	public void setSbcsdm(String sbcsdm) {
		this.sbcsdm = sbcsdm;
	}
	
}



