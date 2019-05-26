package com.jy.moudles.exam.entity;
import java.util.List;

import com.jy.common.entity.BaseEntity;
import com.jy.moudles.examSeason.entity.ExamSeason;

public class Exam extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3230673918646154784L;

	/**
	 * 考试编号
	 */
	private String ksbh;
	
	/**
	 * 考试名称
	 */
	private String ksmc;
	
	/**
	 * 考试类型码
	 */
	private String kslxdm;
	
	/**
	 * 开始日期
	 */
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private String ksksrq;
	
	/**
	 * 结束日期
	 */
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private String ksjsrq;
	
	/**
	 * 主管机构
	 */
	private String zgjgdm;
	
	/**
	 * 场次合集
	 */
	private List<ExamSeason> cc;
	
	
	public String getKsbh() {
		return ksbh;
	}

	public void setKsbh(String ksbh) {
		this.ksbh = ksbh;
	}
	
	public String getKsmc() {
		return ksmc;
	}

	public void setKsmc(String ksmc) {
		this.ksmc = ksmc;
	}
	
	public String getKslxdm() {
		return kslxdm;
	}

	public void setKslxdm(String kslxdm) {
		this.kslxdm = kslxdm;
	}
	
	public String getKsksrq() {
		return ksksrq;
	}

	public void setKsksrq(String ksksrq) {
		this.ksksrq = ksksrq;
	}
	
	public String getKsjsrq() {
		return ksjsrq;
	}

	public void setKsjsrq(String ksjsrq) {
		this.ksjsrq = ksjsrq;
	}
	
	public String getZgjgdm() {
		return zgjgdm;
	}

	public void setZgjgdm(String zgjgdm) {
		this.zgjgdm = zgjgdm;
	}


	public List<ExamSeason> getCc() {
		return cc;
	}

	public void setCc(List<ExamSeason> cc) {
		this.cc = cc;
	}
	
}



